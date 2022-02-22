package com.theovier.athena.client.ecs.prefabs.loaders.components

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class LootComponentLoaderTest {

    @Test
    @DisplayName("Empty <Loot> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/loot/loot_empty_valid")
        Assertions.assertDoesNotThrow {
            LootComponentLoader().load(json)
        }
    }
}