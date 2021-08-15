package com.theovier.athena.client.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.utils.viewport.FitViewport
import com.theovier.athena.client.AthenaGame
import com.theovier.athena.client.ecs.components.aim
import com.theovier.athena.client.ecs.components.transform
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.ecs.systems.*
import com.theovier.athena.client.managers.MapManager
import com.theovier.athena.client.math.xy
import ktx.app.KtxScreen
import ktx.graphics.use
import mu.KotlinLogging


private val log = KotlinLogging.logger {}

class GameScreen(private val game: AthenaGame) : KtxScreen {
    private val steadyReferenceCamera = OrthographicCamera()
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(38f, 23f, camera) //width and height in units, 16:10
    private val engine = PooledEngine()
    private val map = MapManager(camera)
    private val player = Prefab.instantiate("player")
    private val playersCrosshair = Prefab.instantiate("crosshair")

    private val particleEffect = ParticleEffect()



    init {
        initEntities()
        initSystems()
        positionCamera()

        particleEffect.load(
            Gdx.files.local("particles/smoke_trail.p"),
            Gdx.files.local("sprites/particles")
        )
        particleEffect.scaleEffect(AthenaGame.UNIT_SCALE)
        particleEffect.emitters.first().setPosition(camera.position.x, camera.position.y)
        particleEffect.start()
    }

    private fun initEntities() {
        engine.addEntity(player)
        engine.addEntity(playersCrosshair)
    }

    private fun initSystems() {
        engine.apply {
            addSystem(RenderingSystem(game.batch))
            addSystem(CameraMovementSystem(steadyReferenceCamera))
            addSystem(MovementSystem())
            addSystem(PlayerMovementSystem())
            addSystem(PlayerAimSystem())
            addSystem(CrosshairSystem(player.aim))
            addSystem(PlayerAttackSystem())
            addSystem(CameraShakeSystem(steadyReferenceCamera, camera))
            addSystem(LifetimeSystem())
        }
    }

    private fun positionCamera() {
        steadyReferenceCamera.position.set(playersCrosshair.transform.position)
    }

    override fun render(delta: Float) {
        game.batch.use(camera) {
            map.render(delta)
            engine.update(delta)

            particleEffect.update(delta)
            particleEffect.draw(it)
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