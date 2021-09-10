package com.theovier.athena.client.ecs.prefabs.loaders

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.SerializationException
import com.theovier.athena.client.ecs.components.lifetime
import com.theovier.athena.client.ecs.prefabs.loaders.components.MapComponentLoader
import com.theovier.athena.client.ecs.prefabs.loaders.components.PhysicsComponentLoader
import com.theovier.athena.client.ecs.prefabs.loaders.components.SpriteComponentLoader
import ktx.assets.async.AssetStorage
import org.junit.jupiter.api.*
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.junit5.AutoCloseKoinTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EntityLoaderTest: AutoCloseKoinTest() {
    private val componentLoaders = module {
        single { AssetStorage() }
        single { World(Vector2.Zero, true) }
        single { MapComponentLoader(get()) }
        single { SpriteComponentLoader(get()) }
        single { PhysicsComponentLoader(get()) }
    }

    @BeforeEach
    fun startKoin() {
        startKoin {
            modules(componentLoaders)
        }
    }

    @Test
    @DisplayName("Entity with multiple components is loaded correctly")
    fun isEntityWithMultipleComponentsLoaded() {
        val expectedNumberOfComponents = 3
        val loader = PrefabLoader()
        val entity = loader.load("entityWithMultipleComponents")
        Assertions.assertEquals(expectedNumberOfComponents, entity.components.count())
    }

    @Test
    @DisplayName("Entity without components is loaded correctly")
    fun isEmptyEntityLoaded() {
        val expectedNumberOfComponents = 0
        val loader = PrefabLoader()
        val entity = loader.load("emptyEntity")
        Assertions.assertEquals(expectedNumberOfComponents, entity.components.count())
    }

    @Test
    @DisplayName("Configuration is applied correctly")
    fun isConfigurationApplied() {
        val expectedUpdatedLifetime = 3.14f
        val loader = PrefabLoader()
        val entity = loader.load("entityWithLifetime") {
            this.lifetime.duration = expectedUpdatedLifetime
        }
        Assertions.assertEquals(expectedUpdatedLifetime, entity.lifetime.duration)
    }

    @Test
    @DisplayName("Exception is thrown when the entity file could not be located")
    fun throwsExceptionWhenNoFileFound() {
        Assertions.assertThrows(SerializationException::class.java) {
            PrefabLoader().load("notExistingFile")
        }
    }
}