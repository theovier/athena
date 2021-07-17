package com.theovier.athena.client.screens

import com.badlogic.ashley.core.PooledEngine
import com.theovier.athena.client.MyGame
import com.theovier.athena.client.ecs.components.Player
import com.theovier.athena.client.ecs.components.PlayerMovement
import com.theovier.athena.client.ecs.systems.PlayerMovementSystem
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.ashley.with
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class GameScreen(private val game: MyGame) : KtxScreen {
    private val engine = PooledEngine() //should be in the game rather than the screen?

    init {
        engine.entity {
            with<Player>()
            with<PlayerMovement>()
        }
        engine.run {
            addSystem(PlayerMovementSystem())
        }
    }

    override fun show() {

    }

    override fun hide() {

    }

    override fun render(delta: Float) {
        engine.update(delta)
    }

    override fun dispose() {
        log.debug { "'${engine.entities.size()}' entities in engine" }
    }
}