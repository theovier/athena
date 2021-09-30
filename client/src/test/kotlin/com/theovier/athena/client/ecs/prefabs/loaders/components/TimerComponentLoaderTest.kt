package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.theovier.athena.client.ecs.components.Timer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class TimerComponentLoaderTest {

    @Test
    @DisplayName("Empty <Timer> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/timer/timer_empty_valid")
        val component = TimerComponentLoader().load(json)
        Assertions.assertEquals(Timer.DEFAULT_MILLIS_SINCE_START, component.millisSinceStart)
    }

    @Test
    @DisplayName("<Timer> component is loaded correctly")
    fun isComponentLoadedCorrectly() {
        val expectedMillisSinceStart = 123f

        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/timer/timer_valid")
        val component = TimerComponentLoader().load(json)
        Assertions.assertEquals(expectedMillisSinceStart, component.millisSinceStart)
    }
}