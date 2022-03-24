package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.theovier.athena.client.ecs.prefabs.loaders.components.animation.controllers.WiggleAnimationControllerComponentLoader
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class WiggleAnimationControllerComponentLoaderTest {

    @Test
    @DisplayName("Empty <WiggleAnimationController> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/wiggleAnimationController/wiggleAnimationController_empty_valid")
        Assertions.assertDoesNotThrow {
            WiggleAnimationControllerComponentLoader().load(json)
        }
    }
}