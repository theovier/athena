package com.theovier.athena.client

import com.badlogic.gdx.Screen
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.theovier.athena.client.screens.ExampleScreen
import com.theovier.athena.client.screens.LoadingScreen
import ktx.app.KtxGame
import ktx.inject.Context
import ktx.inject.register
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

fun main() {
    val config = Lwjgl3ApplicationConfiguration().apply {
        setWindowSizeLimits(1200, 700, 1920, 1200)
    }
    Lwjgl3Application(MyGame(), config)
}

class MyGame : KtxGame<Screen>() {

    private val context = Context().register {
        bindSingleton(InputActionManger())
    }

    override fun create() {
        addScreen(ExampleScreen(this, context.inject()))
        addScreen(LoadingScreen(this, context.inject()))
        setScreen<LoadingScreen>()
    }

    override fun dispose() {
        super.dispose()
        context.dispose()
    }
}
