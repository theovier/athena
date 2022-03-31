package com.theovier.athena.client.ecs.prefabs.loaders.components

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class TargetableComponentLoaderTest {

    @Test
    @DisplayName("Empty <Targetable> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/targetable/targetable_empty_valid")
        Assertions.assertDoesNotThrow {
            TargetableComponentLoader().load(json)
        }
    }
}