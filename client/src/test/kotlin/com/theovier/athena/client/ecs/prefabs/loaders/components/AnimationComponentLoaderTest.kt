package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.theovier.athena.client.ecs.components.Animation
import com.theovier.athena.client.ecs.prefabs.loaders.components.animation.AnimationComponentLoader
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class AnimationComponentLoaderTest {

    @Test
    @DisplayName("Empty <Animation> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/animation/animation_empty_valid")
        Assertions.assertDoesNotThrow {
            AnimationComponentLoader().load(json)
        }
    }

    @Test
    @DisplayName("Default <Animation> component's name is loaded correctly")
    fun isDefaultNameLoadedCorrectly() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/animation/animation_empty_valid")
        val animation = AnimationComponentLoader().load(json)
        Assertions.assertEquals(Animation.DEFAULT_NAME, animation.name)
    }

    @Test
    @DisplayName("<Animation> component's name is loaded correctly")
    fun isNameLoadedCorrectly() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/animation/animation_valid")
        val animation = AnimationComponentLoader().load(json)
        Assertions.assertEquals("abc", animation.name)
    }
}