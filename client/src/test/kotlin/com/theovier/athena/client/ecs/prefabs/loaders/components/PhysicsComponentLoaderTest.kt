package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.get
import org.koin.test.junit5.AutoCloseKoinTest
import kotlin.test.expect

class PhysicsComponentLoaderTest : AutoCloseKoinTest() {
    private val module = module {
        single { World(Vector2.Zero, true) }
        single { PhysicsComponentLoader(get()) }
    }

    @BeforeEach
    fun startKoin() {
        startKoin {
            modules(module)
        }
    }

    @Test
    @DisplayName("<Physics> component's position is loaded correctly")
    fun isPositionLoaded() {
        val expectedPosition = Vector2(4f, 2f)
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/physics/physics_position_valid")
        val loader = get<PhysicsComponentLoader>()
        val component = loader.load(json)
        val body = component.body
        Assertions.assertEquals(expectedPosition, body.position)
    }

    @Test
    fun isDynamicBodyLoaded() {
        val expectedType = BodyDef.BodyType.DynamicBody
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/physics/physics_dynamic_valid")
        val loader = get<PhysicsComponentLoader>()
        val component = loader.load(json)
        val body = component.body
        Assertions.assertEquals(expectedType, body.type)
    }

    @Test
    fun isStaticBodyLoaded() {
        val expectedType = BodyDef.BodyType.StaticBody
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/physics/physics_static_valid")
        val loader = get<PhysicsComponentLoader>()
        val component = loader.load(json)
        val body = component.body
        Assertions.assertEquals(expectedType, body.type)
    }

    @Test
    fun isKinematicBodyLoaded() {
        val expectedType = BodyDef.BodyType.KinematicBody
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/physics/physics_kinematic_valid")
        val loader = get<PhysicsComponentLoader>()
        val component = loader.load(json)
        val body = component.body
        Assertions.assertEquals(expectedType, body.type)
    }

    @Test
    fun isStaticBodyLoadedWhenNoParameterGiven() {
        val expectedType = BodyDef.BodyType.StaticBody
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/physics/physics_without_type_valid")
        val loader = get<PhysicsComponentLoader>()
        val component = loader.load(json)
        val body = component.body
        Assertions.assertEquals(expectedType, body.type)
    }
}