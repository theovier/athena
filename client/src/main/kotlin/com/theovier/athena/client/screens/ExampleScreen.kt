package com.theovier.athena.client.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.theovier.athena.client.ActionListener
import com.theovier.athena.client.InputAction
import com.theovier.athena.client.InputAction.*
import com.theovier.athena.client.InputActionManger
import ktx.app.KtxScreen
import ktx.graphics.use
import ktx.inject.Context
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class ExampleScreen(private val context: Context) : KtxScreen, ActionListener {
    private val inputActionManager: InputActionManger = context.inject()

    private val font = BitmapFont()
    private val batch = SpriteBatch().apply {
        color = Color.WHITE
    }
    private var message = "Hello Kotlin"

    override fun show() {
        inputActionManager.init() //this seems to be wrong here?!
        inputActionManager.subscribe(this)
    }

    override fun onAction(action: InputAction): Boolean {
        return when(action) {
            UP -> {
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
        inputActionManager.unsubscribe(this)
    }

    override fun dispose() {
        font.dispose()
        batch.dispose()
    }
}