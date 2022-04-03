package com.theovier.athena.client.ecs.prefabs.loaders

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.SerializationException
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.damage.Health
import com.theovier.athena.client.ecs.prefabs.loaders.components.*
import ktx.ashley.get
import ktx.ashley.has
import ktx.assets.async.AssetStorage
import org.junit.jupiter.api.*
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.junit5.AutoCloseKoinTest
import java.lang.IllegalArgumentException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EntityLoaderTest: AutoCloseKoinTest() {
    private val componentLoaders = module {
        single { AssetStorage() }
        single { World(Vector2.Zero, true) }
        single { MapComponentLoader(get()) }
        single { SpineComponentLoader(get()) }
        single { SpriteComponentLoader(get()) }
        single { SoundComponentLoader(get()) }
        single { PhysicsComponentLoader(get()) }
        single { ParticleComponentLoader(get()) }
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
        val entity = loader.loadFromFile("entityWithMultipleComponents")
        Assertions.assertEquals(expectedNumberOfComponents, entity.components.count())
    }

    @Test
    @DisplayName("Entity without components is loaded correctly")
    fun isEmptyEntityLoaded() {
        val expectedNumberOfComponents = 0
        val loader = PrefabLoader()
        val entity = loader.loadFromFile("emptyEntity")
        Assertions.assertEquals(expectedNumberOfComponents, entity.components.count())
    }

    @Test
    @DisplayName("Configuration is applied correctly")
    fun isConfigurationApplied() {
        val expectedUpdatedLifetime = 3.14f
        val loader = PrefabLoader()
        val entity = loader.loadFromFile("entityWithLifetime") {
            this.lifetime.duration = expectedUpdatedLifetime
        }
        Assertions.assertEquals(expectedUpdatedLifetime, entity.lifetime.duration)
    }

    @Test
    @DisplayName("Exception is thrown when the entity file could not be located")
    fun throwsExceptionWhenNoFileFound() {
        Assertions.assertThrows(SerializationException::class.java) {
            PrefabLoader().loadFromFile("notExistingFile")
        }
    }

    @Test
    @DisplayName("Exception is thrown when the entity does not have an id")
    fun throwsExceptionWhenNoEntityIdSet() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            PrefabLoader().loadFromFile("entityWithMissingId")
        }
    }

    @Test
    @DisplayName("Child entity is loaded correctly")
    fun isChildLoaded() {
        val loader = PrefabLoader()
        val entity = loader.loadFromFile("entityWithChild")
        Assertions.assertTrue(entity.hasChildrenComponent)
        Assertions.assertEquals(entity.children.count, 1)
        Assertions.assertNotNull(entity.children.children[0])
    }

    @Test
    @DisplayName("Is component with dependencies on another component loaded correctly")
    fun isComponentWithComponentDependencyLoaded() {
        val loader = PrefabLoader()
        val entity = loader.loadFromFile("componentWithComponentDependency")
        Assertions.assertEquals(entity.components.count(), 2)
        Assertions.assertTrue(entity.has(Transform.MAPPER))
        Assertions.assertTrue(entity.has(Lifetime.MAPPER))
    }

    @Test
    @DisplayName("Is component with dependencies on an entity loaded correctly")
    fun isComponentWithEntityDependencyLoaded() {
        val loader = PrefabLoader()
        val entity = loader.loadFromFile("componentWithEntityDependency")
        Assertions.assertEquals(entity.components.count(), 1)
        Assertions.assertTrue(entity.has(Transform.MAPPER))
    }

    @Test
    @DisplayName("Is component with dependency in children loaded correctly")
    fun isComponentWithDependencyInChildrenLoaded() {
        val loader = PrefabLoader()
        val entity = loader.loadFromFile("componentWithChildDependency")
        Assertions.assertEquals(entity.components.count(), 2)
        Assertions.assertTrue(entity.has(Transform.MAPPER))
        Assertions.assertTrue(entity.has(Children.MAPPER))

        val child = entity.children.children[0]!!
        Assertions.assertTrue(child.has(Transform.MAPPER))
    }

    @Test
    @DisplayName("Is component with unresolvable dependency ignored")
    fun isComponentWithUnresolvableDependencyIgnored() {
        val loader = PrefabLoader()
        val entity = loader.loadFromFile("componentWithMissingDependency")
        Assertions.assertEquals(entity.components.count(), 0)
        Assertions.assertFalse(entity.has(Transform.MAPPER))
    }

    @Test
    @DisplayName("Is a circular dependency detected")
    fun isCircularDependencyDetected() {
        val loader = PrefabLoader()
        val entity = loader.loadFromFile("entityWithCircularDependency")
        Assertions.assertEquals(entity.components.count(), 0)
    }

    @Test
    @DisplayName("Cascading Dependencies are resolved correctly")
    fun isEntityWithCascadingDependenciesLoaded() {
        val loader = PrefabLoader()
        val entity = loader.loadFromFile("entityWithCascadingDependency")
        Assertions.assertEquals(entity.components.count(), 4)
        Assertions.assertTrue(entity.has(Transform.MAPPER))
        Assertions.assertTrue(entity.has(Lifetime.MAPPER))
        Assertions.assertTrue(entity.has(Player.MAPPER))
        Assertions.assertTrue(entity.has(Health.MAPPER))
    }

    @Test
    fun isDependencyPoolClearedAfterLoading() {
        val loader = PrefabLoader()
        val entity1 = loader.loadFromFile("clearDependencyPoolTestEntity_1")
        val entity2 = loader.loadFromFile("clearDependencyPoolTestEntity_2")
        Assertions.assertEquals(entity1.components.count(), 1)
        Assertions.assertEquals(entity2.components.count(), 0)
    }
}