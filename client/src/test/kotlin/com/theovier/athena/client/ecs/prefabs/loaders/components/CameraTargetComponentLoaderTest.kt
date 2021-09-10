package com.theovier.athena.client.ecs.prefabs.loaders.components

import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class CameraTargetComponentLoaderTest {

    @Test
    @DisplayName("Empty <CameraTarget> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/cameraTarget/cameraTarget_valid")
        Assertions.assertDoesNotThrow {
            CameraTargetComponentLoader().load(json)
        }
    }
}