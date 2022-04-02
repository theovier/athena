package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.theovier.athena.client.ecs.components.movement.Dash
import com.theovier.athena.client.ecs.components.movement.Direction
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName

class DashComponentLoaderTest {

    @Test
    @DisplayName("Empty <Dash> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/dash/dash_empty_valid")
        val component = DashComponentLoader().load(json)

        assertEquals(Dash.DEFAULT_SPEED, component.speed)
        assertEquals(Dash.DEFAULT_DURATION, component.duration)
        assertEquals(Dash.DEFAULT_TIME_LEFT, component.timeLeft)
        assertEquals(Dash.DEFAULT_TIME_BETWEEN_DASHES, component.timeBetweenDashes)
        assertEquals(Dash.DEFAULT_CAN_DASH_IN_SECONDS, component.canNextDashInSeconds)
    }

    @Test
    @DisplayName("<Dash> component is loaded correctly")
    fun isComponentLoaded() {
        val expectedSpeed = 1.1f
        val expectedDuration = 2.2f
        val expectedTimeLeft = 3.3f
        val expectedTimeBetweenDashes = 4.4f
        val expectedCanDashInSeconds = 5.5f

        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/dash/dash_valid")
        val dash = DashComponentLoader().load(json)

        assertEquals(expectedSpeed, dash.speed)
        assertEquals(expectedDuration, dash.duration)
        assertEquals(expectedTimeLeft, dash.timeLeft)
        assertEquals(expectedTimeBetweenDashes, dash.timeBetweenDashes)
        assertEquals(expectedCanDashInSeconds, dash.canNextDashInSeconds)
    }
}