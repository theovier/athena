package com.theovier.athena.client.ecs.prefabs.loaders.components

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class AimAssistComponentLoaderTest {

    @Test
    @DisplayName("Empty <AimAssist> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/aimAssist/aimAssist_empty_valid")
        Assertions.assertDoesNotThrow {
            AimAssistComponentLoader().load(json)
        }
    }
}