package com.theovier.athena.client.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.theovier.athena.client.inputs.ActionInputListener
import com.theovier.athena.client.inputs.ActionInput
import com.theovier.athena.client.inputs.ActionInputAdapter
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.graphics.use

class StartupScreen(private val game: KtxGame<Screen>,
                    private val actionInputAdapter: ActionInputAdapter) : KtxScreen, ActionInputListener {

    private val font = BitmapFont()
    private val batch = SpriteBatch().apply {
        color = Color.WHITE
    }
    private val shapeRenderer = ShapeRenderer()

    private var message = "Press W to switch to another screen."
    //private val viewport = ExtendViewport(16f, 9f)
    private val camera = OrthographicCamera(1920f, 1200f)

    override fun show() {
        Gdx.input.inputProcessor = actionInputAdapter
        actionInputAdapter.subscribe(this)
    }

    override fun onAction(action: ActionInput): Boolean {
        return when(action) {
            ActionInput.MOVE_UP -> {
                game.setScreen<GameScreen>()
                true
            }
            else -> false
        }
    }

    override fun render(delta: Float) {
        batch.use(camera) {
            font.draw(it, message, 0f, 0f)
        }

        shapeRenderer.use(ShapeRenderer.ShapeType.Filled, camera) {
            shapeRenderer.circle(100f, 100f, 10f, 100)
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