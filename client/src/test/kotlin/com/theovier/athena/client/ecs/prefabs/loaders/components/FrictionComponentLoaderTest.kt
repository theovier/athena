package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.theovier.athena.client.ecs.components.movement.Friction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class FrictionComponentLoaderTest {

    @Test
    @DisplayName("Empty <Friction> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/friction/friction_empty_valid")
        val component = FrictionComponentLoader().load(json)
        Assertions.assertEquals(Friction.DEFAULT_DECELERATION_FACTOR, component.factor)
    }

    @Test
    @DisplayName("<Friction> component is loaded correctly")
    fun isComponentLoadedCorrectly() {
        val expectedFactor = 123f

        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/friction/friction_valid")
        val component = FrictionComponentLoader().load(json)
        Assertions.assertEquals(expectedFactor, component.factor)
    }
}