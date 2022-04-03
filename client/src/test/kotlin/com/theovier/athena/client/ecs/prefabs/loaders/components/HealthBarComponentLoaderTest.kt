package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.theovier.athena.client.ecs.components.damage.Health
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool
import ktx.ashley.plusAssign
import ktx.assets.async.AssetStorage
import org.junit.jupiter.api.*
import org.koin.dsl.module
import org.koin.test.junit5.AutoCloseKoinTest

class HealthBarComponentLoaderTest: AutoCloseKoinTest() {

    private val componentLoaders = module {
        single { AssetStorage() }
        single { World(Vector2.Zero, true) }
        single { MapComponentLoader(get()) }
        single { SpineComponentLoader(get()) }
        single { SpriteComponentLoader(get()) }
        single { SoundComponentLoader(get()) }
        single { PhysicsComponentLoader(get()) }
    }

    @BeforeEach
    fun startKoin() {
        org.koin.core.context.startKoin {
            modules(componentLoaders)
        }
    }

    @Test
    @DisplayName("<HealthBar> fill reference is loaded correctly")
    fun isComponentLoaded() {
        val entity = Entity()
        val health = Health()
        val transform = Transform()
        entity += health
        entity += transform
        val dependencyPool = DependencyPool()
        dependencyPool.add("root", entity)
        dependencyPool.add("root.health", health)

        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/healthBar/fill_reference_valid")
        val healthBar = HealthBarComponentLoader().load(json, dependencyPool)
        Assertions.assertEquals(healthBar.fillReference, entity)
        Assertions.assertEquals(healthBar.healthReference, health)
    }
}