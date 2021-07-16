package com.theovier.athena.client.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.theovier.athena.client.ActionListener
import com.theovier.athena.client.InputAction
import com.theovier.athena.client.InputActionManger
import com.theovier.athena.client.MyGame
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.graphics.use
import ktx.inject.Context

class LoadingScreen(private val game: KtxGame<Screen>,
                    private val inputActionManager: InputActionManger) : KtxScreen, ActionListener {

    private val font = BitmapFont()
    private val batch = SpriteBatch().apply {
        color = Color.WHITE
    }
    private var message = "Press W to switch to another screen."

    override fun show() {
        Gdx.input.inputProcessor = inputActionManager
        inputActionManager.subscribe(this)
    }

    override fun onAction(action: InputAction): Boolean {
        return when(action) {
            InputAction.MOVE_UP -> {
                game.setScreen<ExampleScreen>()
                true
            }
            else -> false
        }
    }

    override fun render(delta: Float) {
        batch.use {
            font.draw(it, message, 100f, 100f)
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