package com.theovier.athena.client

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.theovier.athena.client.ecs.prefabs.loaders.components.PhysicsComponentLoader
import com.theovier.athena.client.ecs.prefabs.loaders.components.SpriteComponentLoader
import com.theovier.athena.client.ecs.prefabs.loaders.components.MapComponentLoader
import com.theovier.athena.client.ecs.systems.PhysicsSystem
import com.theovier.athena.client.ecs.systems.PlayerAttackSystem
import com.theovier.athena.client.screens.GameScreen
import ktx.assets.async.AssetStorage
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

fun main() {
    val assets = AssetStorage().apply {
        setLoader(suffix = ".tmx") {
            TmxMapLoader()
        }
    }
    val module = module {
        single { assets }
        single { World(Vector2.Zero, true) }
        single { GameScreen(get()) }
        single { PhysicsSystem(get()) }
        single { MapComponentLoader(get()) }
        single { SpriteComponentLoader(get()) }
        single { PhysicsComponentLoader(get()) }
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