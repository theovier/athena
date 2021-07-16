package com.theovier.athena.client.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.theovier.athena.client.inputs.ActionInputListener
import com.theovier.athena.client.inputs.ActionInput
import com.theovier.athena.client.inputs.ActionInput.*
import com.theovier.athena.client.inputs.ActionInputAdapter
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.graphics.use
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class ExampleScreen(private val game: KtxGame<Screen>,
                    private val actionInputAdapter: ActionInputAdapter
) : KtxScreen, ActionInputListener {

    private val font = BitmapFont()
    private val batch = SpriteBatch().apply {
        color = Color.WHITE
    }
    private var message = "Hello Kotlin"

    override fun show() {
        Gdx.input.inputProcessor = actionInputAdapter
        actionInputAdapter.subscribe(this)
    }

    override fun onAction(action: ActionInput): Boolean {
        return when(action) {
            MOVE_UP -> {
                message += "!"
                true
            }
            else -> false
        }
    }

    override fun render(delta: Float) {
        batch.use {
            font.draw(it, message, 500f, 100f)
        }
    }

    override fun hide() {
        actionInputAdapter.unsubscribe(this)
    }

    override fun dispose() {
        font.dispose()
        batch.dispose()
    }
}