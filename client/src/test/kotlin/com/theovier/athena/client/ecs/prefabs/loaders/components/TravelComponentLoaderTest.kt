package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.Travel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class TravelComponentLoaderTest {

    @Test
    @DisplayName("Empty <Travel> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/travel/travel_empty_valid")
        val component = TravelComponentLoader().load(json)
        Assertions.assertEquals(Travel.DEFAULT_ORIGIN_X, component.origin.x)
        Assertions.assertEquals(Travel.DEFAULT_ORIGIN_Y, component.origin.y)
    }

    @Test
    @DisplayName("<Travel> component is loaded correctly")
    fun isComponentLoadedCorrectly() {
        val expectedOrigin = Vector2(1f, 2f)

        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/travel/travel_valid")
        val component = TravelComponentLoader().load(json)
        Assertions.assertEquals(expectedOrigin.x, component.origin.x)
        Assertions.assertEquals(expectedOrigin.y, component.origin.y)
    }
}