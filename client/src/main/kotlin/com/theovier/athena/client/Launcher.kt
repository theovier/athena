package com.theovier.athena.client

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.theovier.athena.client.ecs.prefabs.readers.SpriteComponentReader
import ktx.assets.async.AssetStorage
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

fun main() {
    val module = module {
        single { AssetStorage() }
        single { SpriteComponentReader(get()) }
    }
    startKoin {
        modules(module)
    }
    val config = Lwjgl3ApplicationConfiguration().apply {
        setTitle("Athena")
        setWindowSizeLimits(1920, 1200, 1920, 1200)
    }
    Lwjgl3Application(AthenaGame(), config)
}