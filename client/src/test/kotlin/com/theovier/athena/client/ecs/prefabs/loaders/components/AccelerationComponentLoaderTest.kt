package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.theovier.athena.client.ecs.components.movement.Acceleration
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AccelerationComponentLoaderTest {

    @Test
    @DisplayName("Empty <Acceleration> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/acceleration/acceleration_empty_valid")
        val component = AccelerationComponentLoader().load(json)
        Assertions.assertEquals(Acceleration.DEFAULT_ACCELERATION_X, component.acceleration.x)
        Assertions.assertEquals(Acceleration.DEFAULT_ACCELERATION_Y, component.acceleration.y)
        Assertions.assertEquals( Acceleration.DEFAULT_ACCELERATION_FACTOR, component.accelerationFactor)
    }

    @Test
    @DisplayName("<Acceleration> component is loaded correctly")
    fun isComponentLoadedCorrectly() {
        val expectedAccelerationX = 1f
        val expectedAccelerationY = 2f
        val expectedAccelerationFactor = 3f

        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/acceleration/acceleration_valid")
        val component = AccelerationComponentLoader().load(json)
        Assertions.assertEquals(expectedAccelerationX, component.acceleration.x)
        Assertions.assertEquals(expectedAccelerationY, component.acceleration.y)
        Assertions.assertEquals(expectedAccelerationFactor, component.accelerationFactor)
    }
}