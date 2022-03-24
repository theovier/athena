package com.theovier.athena.client.ecs.prefabs.loaders.components

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class WiggleComponentLoaderTest {

    @Test
    @DisplayName("Empty <Wiggle> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/wiggle/wiggle_empty_valid")
        Assertions.assertDoesNotThrow {
            WiggleComponentLoader().load(json)
        }
    }
}