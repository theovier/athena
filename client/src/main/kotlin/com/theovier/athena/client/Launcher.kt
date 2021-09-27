package com.theovier.athena.client

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.theovier.athena.client.ecs.components.Input
import com.theovier.athena.client.ecs.prefabs.loaders.components.*
import com.theovier.athena.client.ecs.systems.InputSystem
import com.theovier.athena.client.ecs.systems.physics.PhysicsSystem
import com.theovier.athena.client.ecs.systems.player.PlayerAttackSystem
import com.theovier.athena.client.ecs.systems.player.PlayerMovementSystem
import com.theovier.athena.client.loaders.spine.AnimationStateDataLoader
import com.theovier.athena.client.loaders.spine.SkeletonDataLoader
import com.theovier.athena.client.screens.GameScreen
import ktx.assets.async.AssetStorage
import ktx.freetype.async.registerFreeTypeFontLoaders
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

fun main() {
    val assets = AssetStorage().apply {
        setLoader(suffix = ".tmx") {
            TmxMapLoader()
        }
        setLoader {
            SkeletonDataLoader()
        }
        setLoader {
            AnimationStateDataLoader()
        }
        registerFreeTypeFontLoaders()
    }
    val module = module {
        single { assets }
        single { World(Vector2.Zero, true) }
        single { Input() }
        single { GameScreen(get()) }
        single { PhysicsSystem(get()) }
        single { InputSystem(get()) }
        single { PlayerAttackSystem(get()) }
        single { PlayerMovementSystem(get()) }
        single { MapComponentLoader(get()) }
        single { SpineComponentLoader(get()) }
        single { SpriteComponentLoader(get()) }
        single { SoundComponentLoader(get()) }
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