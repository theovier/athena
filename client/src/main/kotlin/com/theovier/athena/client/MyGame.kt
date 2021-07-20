package com.theovier.athena.client

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.theovier.athena.client.inputs.ActionInputManager
import com.theovier.athena.client.screens.GameScreen
import com.theovier.athena.client.screens.StartupScreen
import kotlinx.coroutines.launch
import ktx.app.KtxGame
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.inject.Context
import ktx.inject.register
import org.w3c.dom.Text

class MyGame : KtxGame<Screen>() {
    val batch: Batch by lazy {
        SpriteBatch()
    }

    val assetStorage = AssetStorage()

    private val context = Context().register {
        bindSingleton(ActionInputManager())
    }

    override fun create() {
        super.create()
        KtxAsync.initiate()
        assetStorage.loadSync<Texture>("sprites/test.png")
        addScreen(GameScreen(this))
        setScreen<GameScreen>()
    }


    override fun dispose() {
        super.dispose()
        batch.dispose()
        context.dispose()
        assetStorage.dispose()
    }

    companion object {
        val UNIT_SCALE = 1 / 64f // 1 unit = 64 pixels
    }
}
