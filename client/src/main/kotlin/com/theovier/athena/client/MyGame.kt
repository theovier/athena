package com.theovier.athena.client

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

fun main() {
    log.debug { "Hello World " }

    val config = Lwjgl3ApplicationConfiguration().apply {
        setWindowSizeLimits(1200, 700, 1920, 1200)
    }

    Lwjgl3Application(MyApplicationListener(), config)
}

