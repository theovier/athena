package com.theovier.athena.client.ecs.prefabs.loaders.components

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class PostponeAudioComponentLoaderTest  {

    @Test
    @DisplayName("Empty <PostponeAudio> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/aim/aim_valid")
        Assertions.assertDoesNotThrow {
            PostponeAudioComponentLoader().load(json)
        }
    }
}