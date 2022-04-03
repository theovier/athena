package com.theovier.athena.client.ecs.prefabs.loaders.components

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName

class CriticalHitChanceComponentLoaderTest {

    @Test
    @DisplayName("Empty <CriticalHitChance> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/criticalHitChance/critChance_empty_valid")
        assertDoesNotThrow {
            CriticalHitChanceComponentLoader().load(json)
        }
    }

    @Test
    @DisplayName("<CriticalHitChance> component is loaded correctly")
    fun isComponentLoadedCorrectly() {
        val expectedChance = 0.5f

        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/criticalHitChance/critChance_valid")
        val component = CriticalHitChanceComponentLoader().load(json)
        assertEquals(expectedChance, component.chance)
    }
}