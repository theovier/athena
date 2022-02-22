package com.theovier.athena.client

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.theovier.athena.client.screens.GameScreen
import kotlinx.coroutines.launch
import ktx.app.KtxGame
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.freetype.async.loadFreeTypeFont
import ktx.scene2d.Scene2DSkin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.lang.RuntimeException


class AthenaGame : KtxGame<Screen>(), KoinComponent {
    private val assets: AssetStorage by inject()
    private val gameScreen: GameScreen by inject()

    override fun create() {
        super.create()
        enforceConnectedController()
        KtxAsync.initiate()
        Gdx.input.inputProcessor = InputMultiplexer()
        loadAssets()
        setDefaultScene2DSkin()
        addScreen(gameScreen)
        setScreen<GameScreen>()
    }

    private fun enforceConnectedController() {
        if (Controllers.getCurrent() == null) {
            throw RuntimeException("You can currently only play with a connected controller - I was too lazy. Sorry :(")
        }
    }

    private fun loadAssets() {
        assets.loadSync<Texture>("sprites/bullet.png")
        assets.loadSync<Texture>("sprites/loot/dufflebag.png")
        assets.loadSync<Texture>("sprites/loot/dufflebag_02.png")
        assets.loadSync<Skin>("ui/skins/default/uiskin.json")
        assets.loadSync<FreeTypeFontGenerator>("fonts/open-sans.regular.ttf")
        assets.loadSync<Sound>("audio/gun_fire-01.ogg")
        assets.loadSync<Sound>("audio/gun_fire-02.ogg")
        assets.loadSync<Sound>("audio/gun_fire-03.ogg")
    }

    private fun setDefaultScene2DSkin() {
        Scene2DSkin.defaultSkin = assets["ui/skins/default/uiskin.json"]
    }

    override fun dispose() {
        super.dispose()
        assets.dispose()
    }

    companion object {
        const val UNIT_SCALE = 1 / 64f // 1 unit = 64 pixels
    }
}
