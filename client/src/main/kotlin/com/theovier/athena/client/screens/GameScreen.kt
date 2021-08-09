package com.theovier.athena.client.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector3
import com.theovier.athena.client.AthenaGame
import ktx.app.KtxScreen
import com.badlogic.gdx.utils.viewport.FitViewport
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.ecs.systems.*
import ktx.ashley.entity
import ktx.ashley.with
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class GameScreen(private val game: AthenaGame) : KtxScreen {
    private val playerStartingPosition = Vector3(10f, 12f, 0f)
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(38f, 23f, camera) //width and height in units, 16:10
    private val engine = PooledEngine().apply {
        addSystem(RenderingSystem(game.batch, viewport))
        addSystem(MovementSystem())
        addSystem(PlayerMovementSystem())
        addSystem(PlayerAttackSystem(game, viewport))
        addSystem(CameraMovementSystem(camera, playerStartingPosition))
        addSystem(CameraShakeSystem(viewport))
        addSystem(LifetimeSystem())
    }

    init {
        val entityFromPrefab = Prefab.instantiate("player")
        engine.addEntity(entityFromPrefab)

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
    }

    override fun render(delta: Float) {
        super.render(delta)
        engine.update(delta)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        viewport.update(width, height)
    }

    override fun dispose() {
        log.debug { "'${engine.entities.size()}' entities in engine" }
    }
}