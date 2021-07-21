package com.theovier.athena.client.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.theovier.athena.client.AthenaGame
import com.theovier.athena.client.ecs.systems.RenderingSystem
import ktx.app.KtxScreen
import com.badlogic.gdx.utils.viewport.FitViewport
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.systems.PlayerMovementSystem
import ktx.ashley.entity
import ktx.ashley.with
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class GameScreen(private val game: AthenaGame) : KtxScreen {
    private val viewport = FitViewport(38f, 23f) //width and height in units, 16:10
    private val engine = PooledEngine().apply {
        addSystem(RenderingSystem(game.batch, viewport))
        addSystem(PlayerMovementSystem())
    }

    init {
        engine.entity {
            with<Player>()
            with<Movement>() {
                speed = 7
            }
            with<Transform>() {
                position.set(18f, 11f, 0f)
                size.set(2f, 2f)
            }
            with<SpriteRenderer>() {
                val texture: Texture = game.assetStorage["sprites/test.png"]
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
        viewport.update(width, height, true)
    }

    override fun dispose() {
        log.debug { "'${engine.entities.size()}' entities in engine" }
    }
}