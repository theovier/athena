package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.theovier.athena.client.ecs.prefabs.loaders.components.animation.controllers.PlayerAnimationControllerComponentLoader
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class PlayerAnimationControllerComponentLoaderTest {

    @Test
    @DisplayName("Empty <PlayerAnimationController> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/playerAnimationController/playerAnimationController_empty_valid")
        Assertions.assertDoesNotThrow {
            PlayerAnimationControllerComponentLoader().load(json)
        }
    }
}