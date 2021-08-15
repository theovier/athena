package com.theovier.athena.client.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.FitViewport
import com.theovier.athena.client.AthenaGame
import com.theovier.athena.client.ecs.components.aim
import com.theovier.athena.client.ecs.components.transform
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.ecs.systems.*
import com.theovier.athena.client.managers.MapManager
import ktx.app.KtxScreen
import ktx.graphics.use
import mu.KotlinLogging


private val log = KotlinLogging.logger {}

class GameScreen(private val game: AthenaGame) : KtxScreen {
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(38f, 23f, camera) //width and height in units, 16:10
    private val engine = PooledEngine()
    private lateinit var map: MapManager
    private val player = Prefab.instantiate("player")
    private val playersCrosshair = Prefab.instantiate("crosshair")

    init {
        initEntities()
        initSystems()
        initManagers()
        positionCamera()
    }

    private fun initEntities() {
        engine.addEntity(player)
        engine.addEntity(playersCrosshair)
    }

    private fun initSystems() {
        engine.apply {
            addSystem(RenderingSystem(game.batch))
            addSystem(CameraMovementSystem(camera))
            addSystem(MovementSystem())
            addSystem(PlayerMovementSystem())
            addSystem(PlayerAimSystem())
            addSystem(CrosshairSystem(player.aim))
            addSystem(PlayerAttackSystem())
            addSystem(CameraShakeSystem(viewport))
            addSystem(LifetimeSystem())
        }
    }

    private fun initManagers() {
        //todo has to be init after the CameraShake system until the camera shake system gets reworked.
        map = MapManager(viewport.camera as OrthographicCamera)
    }

    private fun positionCamera() {
        camera.position.set(playersCrosshair.transform.position)
    }

    override fun render(delta: Float) {
        game.batch.use(viewport.camera) {
            map.render(delta)
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