package com.theovier.athena.client

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.theovier.athena.client.inputs.ActionInputManager
import com.theovier.athena.client.screens.GameScreen
import com.theovier.athena.client.screens.StartupScreen
import ktx.app.KtxGame
import ktx.inject.Context
import ktx.inject.register

class MyGame : KtxGame<Screen>() {
    private val batch: Batch by lazy {
        SpriteBatch()
    }

    private val context = Context().register {
        bindSingleton(ActionInputManager())
    }

    override fun create() {
        super.create()
        addScreen(GameScreen(this))
        addScreen(StartupScreen(this, context.inject<ActionInputManager>()))
        setScreen<StartupScreen>()
    }

    override fun render() {
        super.render()
    }

    override fun dispose() {
        super.dispose()
        batch.dispose()
        context.dispose()
    }
}
