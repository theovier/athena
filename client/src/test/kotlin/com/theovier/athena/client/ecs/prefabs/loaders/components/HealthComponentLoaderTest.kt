package com.theovier.athena.client.ecs.prefabs.loaders.components

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class HealthComponentLoaderTest {

    @Test
    @DisplayName("<Health> component is loaded correctly")
    fun isComponentLoaded() {
        val expectedMaximum = 5
        val expectedCurrent = 3
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/health/health_valid")
        val health = HealthComponentLoader().load(json)
        Assertions.assertEquals(expectedMaximum, health.maximum)
        Assertions.assertEquals(expectedCurrent, health.current)
    }

    @Test
    @DisplayName("<Health> current = maximum if current was omitted")
    fun doesCurrentEqualMaximumIfCurrentOmitted() {
        val expectedMaximum = 5
        val expectedCurrent = 5
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/health/health_without_current_valid")
        val health = HealthComponentLoader().load(json)
        Assertions.assertEquals(expectedMaximum, health.maximum)
        Assertions.assertEquals(expectedCurrent, health.current)
    }
}