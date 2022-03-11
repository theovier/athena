package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.theovier.athena.client.ecs.components.Fade
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class FadeComponentLoaderTest {

    @Test
    @DisplayName("Empty <Fade> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/fade/fade_empty_valid")
        val component = FadeComponentLoader().load(json)
        Assertions.assertEquals(Fade.DEFAULT_START, component.start)
        Assertions.assertEquals(Fade.DEFAULT_END, component.end)
        Assertions.assertEquals(Fade.DEFAULT_CURRENT, component.current)
        Assertions.assertEquals(Fade.DEFAULT_DURATION, component.duration)
    }

    @Test
    @DisplayName("<Fade> component is loaded correctly")
    fun isComponentLoadedCorrectly() {
        val expectedStart = 100f
        val expectedEnd = 0f
        val expectedCurrent = 20f
        val expectedDuration = 5f

        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/fade/fade_valid")
        val component = FadeComponentLoader().load(json)
        Assertions.assertEquals(expectedStart, component.start)
        Assertions.assertEquals(expectedEnd, component.end)
        Assertions.assertEquals(expectedCurrent, component.current)
        Assertions.assertEquals(expectedDuration, component.duration)
    }
}