package com.theovier.athena.client.ecs.prefabs.loaders.components

import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class AimComponentLoaderTest {

    @Test
    @DisplayName("Empty <Aim> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/aim/aim_valid")
        Assertions.assertDoesNotThrow {
            AimComponentLoader().load(json)
        }
    }
}