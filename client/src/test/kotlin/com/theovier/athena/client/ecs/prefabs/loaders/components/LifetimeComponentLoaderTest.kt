package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.theovier.athena.client.ecs.components.Lifetime
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class LifetimeComponentLoaderTest {

    @Test
    @DisplayName("Empty <Lifetime> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/lifetime/lifetime_empty_valid")
        val component = LifetimeComponentLoader().load(json)
        Assertions.assertEquals(Lifetime.DEFAULT_DURATION, component.duration)
    }

    @Test
    @DisplayName("<Lifetime> component is loaded correctly")
    fun isComponentLoadedCorrectly() {
        val expectedDuration = 4.2f
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/lifetime/lifetime_valid")
        val component = LifetimeComponentLoader().load(json)
        Assertions.assertEquals(expectedDuration, component.duration)
    }
}