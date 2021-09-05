package com.theovier.athena.client

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.theovier.athena.client.screens.GameScreen
import ktx.app.KtxGame
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.scene2d.Scene2DSkin
import java.lang.RuntimeException


class AthenaGame : KtxGame<Screen>() {
    override fun create() {
        super.create()
        enforceConnectedController()
        Gdx.input.inputProcessor = InputMultiplexer()
        KtxAsync.initiate()
        loadAssets()
        setDefaultScene2DSkin()
        addScreen(GameScreen())
        setScreen<GameScreen>()
    }

    private fun enforceConnectedController() {
        if (Controllers.getCurrent() == null) {
            throw RuntimeException("You can currently only play with a connected controller - I was too lazy. Sorry :(")
        }
    }

    private fun loadAssets() {
        //better to load assets in a loading screen
        assetStorage.loadSync<Texture>("sprites/bullet.png")
        assetStorage.loadSync<Skin>("ui/skins/default/uiskin.json")
    }

    private fun setDefaultScene2DSkin() {
        Scene2DSkin.defaultSkin = assetStorage["ui/skins/default/uiskin.json"]
    }


    override fun dispose() {
        super.dispose()
        assetStorage.dispose()
    }

    companion object {
        val assetStorage = AssetStorage() //todo: think of a better way to access this in the SpriteSerializer
        const val UNIT_SCALE = 1 / 64f // 1 unit = 64 pixels
    }
}
