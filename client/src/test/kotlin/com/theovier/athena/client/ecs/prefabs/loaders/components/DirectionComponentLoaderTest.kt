package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.theovier.athena.client.ecs.components.movement.Direction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class DirectionComponentLoaderTest {

    @Test
    @DisplayName("Empty <Direction> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/direction/direction_empty_valid")
        val component = DirectionComponentLoader().load(json)
        Assertions.assertEquals(Direction.DEFAULT_DIRECTION_X, component.direction.x)
        Assertions.assertEquals(Direction.DEFAULT_DIRECTION_Y, component.direction.y)
    }

    @Test
    @DisplayName("<Acceleration> component is loaded correctly")
    fun isComponentLoadedCorrectly() {
        val expectedADirectionX = 1f
        val expectedDirectionY = 2f

        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/direction/direction_valid")
        val component = DirectionComponentLoader().load(json)
        Assertions.assertEquals(expectedADirectionX, component.direction.x)
        Assertions.assertEquals(expectedDirectionY, component.direction.y)
    }
}