package com.theovier.athena.client.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.theovier.athena.client.MyGame
import com.theovier.athena.client.ecs.components.Player
import com.theovier.athena.client.ecs.components.PlayerMovement
import com.theovier.athena.client.ecs.components.SpriteRenderer
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.systems.PlayerMovementSystem
import com.theovier.athena.client.ecs.systems.RenderingSystem
import kotlinx.coroutines.launch
import ktx.app.KtxScreen
import ktx.ashley.addComponent
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.ashley.entity
import ktx.ashley.with
import ktx.async.KtxAsync
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class GameScreen(private val game: MyGame) : KtxScreen {

    val UNIT_SCALE = 1 / 16f

    private val viewport = FitViewport(16f, 9f)
    private val engine = PooledEngine().apply {
        addSystem(RenderingSystem(game.batch, viewport))
    }

    private val debugTexture: Texture by lazy {
        val pixmap = Pixmap((1 / UNIT_SCALE).toInt(), (1 / UNIT_SCALE).toInt(), Pixmap.Format.RGB888).apply {
            setColor(1f, 0f, 0f, 1f)
            fill()
        }
        Texture(pixmap)
    }

    init {
        engine.entity {
            with<Player>()
            with<Transform>() {
                position.set(3f, 3f, 0f)
                size.set(1f, 2f)
            }
            with<SpriteRenderer>() {
                sprite.texture = debugTexture
                sprite.setSize(1f, 1f)
                sprite.setOrigin(0.5f, 0.5f)
            }
        }

        engine.entity {
            with<Player>()
            with<Transform>() {
                position.set(5f, 5f, 0f)
            }
            with<SpriteRenderer>() {
                sprite.texture = debugTexture
                sprite.setSize(UNIT_SCALE * debugTexture.width, UNIT_SCALE * debugTexture.height)
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