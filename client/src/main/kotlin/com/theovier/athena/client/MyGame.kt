package com.theovier.athena.client

import com.badlogic.gdx.Screen
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.theovier.athena.client.screens.ExampleScreen
import ktx.app.KtxGame
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

fun main() {
    val config = Lwjgl3ApplicationConfiguration().apply {
        setWindowSizeLimits(1200, 700, 1920, 1200)
    }
    Lwjgl3Application(ExampleGame(), config)
}

class ExampleGame : KtxGame<Screen>() {
    override fun create() {
        addScreen(ExampleScreen())
        setScreen<ExampleScreen>()
    }
}
