package com.theovier.athena.client

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.theovier.athena.client.inputs.ActionInputManager
import com.theovier.athena.client.screens.GameScreen
import ktx.app.KtxGame
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.inject.Context
import ktx.inject.register

class AthenaGame : KtxGame<Screen>() {
    val batch: Batch by lazy {
        SpriteBatch()
    }

    val assetStorage = AssetStorage()

    override fun create() {
        super.create()
        Gdx.input.inputProcessor = InputMultiplexer()
        KtxAsync.initiate()
        loadAssets()
        addScreen(GameScreen(this))
        setScreen<GameScreen>()
    }

    private fun loadAssets() {
        //better to load assets in a loading screen
        assetStorage.loadSync<Texture>("sprites/test.png")
        assetStorage.loadSync<Texture>("sprites/cyan_square.png")
        assetStorage.loadSync<Texture>("sprites/bullet.png")
    }

    override fun dispose() {
        super.dispose()
        batch.dispose()
        assetStorage.dispose()
    }

    companion object {
        const val UNIT_SCALE = 1 / 64f // 1 unit = 64 pixels
    }
}
