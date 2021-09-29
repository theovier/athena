package com.theovier.athena.client.ecs.prefabs.loaders.components

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class ForegroundComponentLoaderTest {

    @Test
    @DisplayName("Empty <Foreground> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/foreground/foreground_valid")
        Assertions.assertDoesNotThrow {
            ForegroundComponentLoader().load(json)
        }
    }
}