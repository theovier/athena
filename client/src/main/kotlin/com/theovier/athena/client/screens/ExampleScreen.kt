package com.theovier.athena.client.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.app.KtxInputAdapter
import ktx.app.KtxScreen
import ktx.graphics.use
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class ExampleScreen : KtxScreen, KtxInputAdapter {
    private val font = BitmapFont()
    private val batch = SpriteBatch().apply {
        color = Color.WHITE
    }
    private var message = "Hello Kotlin"

    override fun show() {
        super.show()
        //val inputProcessor = Gdx.input.inputProcessor //is null currently, dont add to the multiplexer
        val multiplexer = InputMultiplexer(this)
        Gdx.input.inputProcessor = multiplexer
    }

    override fun keyDown(keycode: Int): Boolean {
        when(keycode) {
            Input.Keys.E -> {
                log.debug { "e pressed" }
                message += "!"
            }
            else -> return false
        }
        return true
    }

    override fun render(delta: Float) {
        batch.use {
            font.draw(it, message, 500f, 100f)
        }
    }

    override fun dispose() {
        font.dispose()
        batch.dispose()
    }
}