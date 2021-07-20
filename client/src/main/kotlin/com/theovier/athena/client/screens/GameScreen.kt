package com.theovier.athena.client.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Texture
import com.theovier.athena.client.MyGame
import com.theovier.athena.client.ecs.components.Player
import com.theovier.athena.client.ecs.components.SpriteRenderer
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.systems.RenderingSystem
import ktx.app.KtxScreen
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.ashley.entity
import ktx.ashley.with
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class GameScreen(private val game: MyGame) : KtxScreen {

    private val viewport = FitViewport(64f, 40f) //width and height in units, 16:10
    private val engine = PooledEngine().apply {
        addSystem(RenderingSystem(game.batch, viewport))
    }

    init {
        engine.entity {
            with<Player>()
            with<Transform>() {
                position.set(26f, 14f, 0f)
                size.set(6f, 6f)
            }
            with<SpriteRenderer>() {
                val texture: Texture = game.assetStorage["sprites/test.png"]
                sprite.setRegion(texture)
                sprite.setSize(texture.width * MyGame.UNIT_SCALE, texture.height * MyGame.UNIT_SCALE)
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