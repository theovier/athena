package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.theovier.athena.client.ecs.components.Movement
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class MovementComponentLoaderTest {

    @Test
    @DisplayName("Empty <Movement> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/movement/movement_empty_valid")
        val component = MovementComponentLoader().load(json)
        Assertions.assertEquals(Movement.DEFAULT_MASS, component.mass)
        Assertions.assertEquals(Movement.DEFAULT_ACCELERATION_FACTOR, component.accelerationFactor)
        Assertions.assertEquals(Movement.DEFAULT_DECELERATION_FACTOR, component.decelerationFactor)
        Assertions.assertEquals(Movement.DEFAULT_MAX_SPEED, component.maxSpeed)
        Assertions.assertEquals(Movement.DEFAULT_STANDING_STILL_THRESHOLD, component.standingStillThreshold)
    }

    @Test
    @DisplayName("<Movement> component is loaded correctly")
    fun isComponentLoadedCorrectly() {
        val expectedMass = 0f
        val expectedAccelerationFactor = 1f
        val expectedDecelerationFactor = 2f
        val expectedMaxSpeed = 3f
        val expectedStandingStillThreshold = 4f
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/movement/movement_valid")
        val component = MovementComponentLoader().load(json)
        Assertions.assertEquals(expectedMass, component.mass)
        Assertions.assertEquals(expectedAccelerationFactor, component.accelerationFactor)
        Assertions.assertEquals(expectedDecelerationFactor, component.decelerationFactor)
        Assertions.assertEquals(expectedMaxSpeed, component.maxSpeed)
        Assertions.assertEquals(expectedStandingStillThreshold, component.standingStillThreshold)
    }
}