package com.theovier.athena.client.ecs.prefabs.loaders.components

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class IgnoreAudioComponentLoaderTest  {

    @Test
    @DisplayName("Empty <IgnoreAudio> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/ignoreAudio/ignoreAudio_valid")
        Assertions.assertDoesNotThrow {
            IgnoreAudioComponentLoader().load(json)
        }
    }
}