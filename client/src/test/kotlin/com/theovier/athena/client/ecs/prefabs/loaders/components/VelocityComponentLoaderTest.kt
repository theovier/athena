package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.movement.Velocity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class VelocityComponentLoaderTest {

    @Test
    @DisplayName("Empty <Velocity> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/velocity/velocity_empty_valid")
        val component = VelocityComponentLoader().load(json)
        Assertions.assertEquals(Velocity.DEFAULT_VELOCITY_X, component.velocity.x)
        Assertions.assertEquals(Velocity.DEFAULT_VELOCITY_Y, component.velocity.y)
        Assertions.assertEquals(Velocity.DEFAULT_MAX_SPEED, component.maxSpeed)
        Assertions.assertEquals(Velocity.DEFAULT_STANDING_STILL_THRESHOLD, component.standingStillThreshold)
    }

    @Test
    @DisplayName("<Velocity> component is loaded correctly")
    fun isComponentLoadedCorrectly() {
        val expectedVelocity = Vector2(1f, 2f)
        val expectedMaxSpeed = 3f
        val expectedStandingStillThreshold = 4f

        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/velocity/velocity_valid")
        val component = VelocityComponentLoader().load(json)
        Assertions.assertEquals(expectedVelocity, component.velocity)
        Assertions.assertEquals(expectedMaxSpeed, component.maxSpeed)
        Assertions.assertEquals(expectedStandingStillThreshold, component.standingStillThreshold)
    }
}