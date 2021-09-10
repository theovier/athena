package com.theovier.athena.client.ecs.prefabs.loaders

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Movement

class MovementComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Component {
        val component = Movement().apply {
            maxSpeed = componentJSON.getFloat("maxSpeed", 0f)
            accelerationFactor = componentJSON.getFloat("acceleration", 0f)
            decelerationFactor = componentJSON.getFloat("deceleration", 0f)
            mass = componentJSON.getFloat("mass", 1f)
            standingStillThreshold = componentJSON.getFloat("standingStillThreshold", 0.1f)
        }
        if (componentJSON.has(DIRECTION)) {
            val directionJSON = componentJSON.get(DIRECTION)
            val direction = ComponentLoader.readVector2(directionJSON)
            component.direction.set(direction)
        }
        return component
    }

    companion object {
        const val DIRECTION = "direction"
    }
}