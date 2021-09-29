package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.movement.Direction

class DirectionComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Component {
        return Direction().apply {
            direction.x = componentJSON.getFloat(DIRECTION_X, Direction.DEFAULT_DIRECTION_X)
            direction.y = componentJSON.getFloat(DIRECTION_Y, Direction.DEFAULT_DIRECTION_Y)
        }
    }

    companion object {
        const val DIRECTION_X = "x"
        const val DIRECTION_Y = "y"
    }
}