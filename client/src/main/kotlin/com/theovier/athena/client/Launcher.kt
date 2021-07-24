package com.theovier.athena.client

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

fun main() {
    val config = Lwjgl3ApplicationConfiguration().apply {
        setTitle("Athena")
        setWindowSizeLimits(1920, 1200, 1920, 1200)
    }
    Lwjgl3Application(AthenaGame(), config)
}