package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.theovier.athena.client.ecs.components.DustTrail
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class DustTrailComponentLoaderTest {

    @Test
    @DisplayName("Empty <DustTrail> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/dustTrail/dustTrail_empty_valid")
        val component = DustTrailComponentLoader().load(json)
        Assertions.assertEquals(DustTrail.DEFAULT_SPAWN_FREQUENCY_IN_SECONDS, component.spawnFrequency)
    }

    @Test
    @DisplayName("<DustTrail> component is loaded correctly")
    fun isComponentLoadedCorrectly() {
        val expectedFrequency = 4.2f
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/dustTrail/dustTrail_valid")
        val component = DustTrailComponentLoader().load(json)
        Assertions.assertEquals(expectedFrequency, component.spawnFrequency)
    }
}