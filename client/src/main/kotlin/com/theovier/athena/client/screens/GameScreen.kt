package com.theovier.athena.client.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.theovier.athena.client.AthenaGame
import com.theovier.athena.client.ecs.components.SpriteRenderer
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.aim
import com.theovier.athena.client.ecs.components.transform
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.ecs.systems.*
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.ashley.with
import ktx.graphics.use
import mu.KotlinLogging


private val log = KotlinLogging.logger {}

class GameScreen(private val game: AthenaGame) : KtxScreen {
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(38f, 23f, camera) //width and height in units, 16:10
    private val engine = PooledEngine()
    private val map = TmxMapLoader().load("maps/debug.tmx")
    private val mapRenderer = OrthogonalTiledMapRenderer(map, AthenaGame.UNIT_SCALE)

    init {
        val player = Prefab.instantiate("player")
        val crosshair = Prefab.instantiate("crosshair")

        engine.addEntity(player)
        engine.addEntity(crosshair)

        //reference object
        engine.entity {
            with<Transform> {
                position.set(14f, 10f, 0f)
            }
            with<SpriteRenderer> {
                val texture: Texture = AthenaGame.assetStorage["sprites/cyan_square.png"]
                sprite.setRegion(texture)
                sprite.setSize(texture.width * AthenaGame.UNIT_SCALE, texture.height * AthenaGame.UNIT_SCALE)
                sprite.setOrigin(sprite.width * 0.5f, sprite.height * 0.5f)
            }
        }

        engine.apply {
            addSystem(RenderingSystem(game.batch))
            addSystem(MovementSystem())
            addSystem(PlayerMovementSystem())
            addSystem(PlayerAimSystem())
            addSystem(CrosshairSystem(player.aim))
            addSystem(PlayerAttackSystem())
            addSystem(CameraMovementSystem(camera, crosshair.transform.position))
            addSystem(CameraShakeSystem(viewport))
            addSystem(LifetimeSystem())
        }
    }

    override fun render(delta: Float) {
        game.batch.use(viewport.camera) {
            //todo externalize the map rendering process
            //todo get rid of cast by using the camera obj. (req. rework of CameraMovement)
            mapRenderer.setView(viewport.camera as OrthographicCamera)
            mapRenderer.render()
            engine.update(delta)
        }
        viewport.apply()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        viewport.update(width, height)
    }

    override fun dispose() {
        log.debug { "'${engine.entities.size()}' entities in engine" }
    }
}