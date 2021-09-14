package com.theovier.athena.client.ecs.prefabs.loaders.components

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class PlayerComponentLoaderTest {

    @Test
    @DisplayName("Empty <Player> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/player/player_valid")
        Assertions.assertDoesNotThrow {
            PlayerComponentLoader().load(json)
        }
    }
}