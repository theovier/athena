package com.theovier.athena.client.ecs.prefabs.loaders.components

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class CrosshairComponentLoaderTest {

    @Test
    @DisplayName("Empty <Crosshair> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/crosshair/crosshair_valid")
        Assertions.assertDoesNotThrow {
            CrosshairComponentLoader().load(json)
        }
    }
}