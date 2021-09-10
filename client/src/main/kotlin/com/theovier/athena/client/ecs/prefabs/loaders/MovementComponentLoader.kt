package com.theovier.athena.client.ecs.prefabs.loaders

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Movement
import com.theovier.athena.client.ecs.components.Movement.Companion.DEFAULT_ACCELERATION_FACTOR
import com.theovier.athena.client.ecs.components.Movement.Companion.DEFAULT_DECELERATION_FACTOR
import com.theovier.athena.client.ecs.components.Movement.Companion.DEFAULT_MASS
import com.theovier.athena.client.ecs.components.Movement.Companion.DEFAULT_MAX_SPEED
import com.theovier.athena.client.ecs.components.Movement.Companion.DEFAULT_STANDING_STILL_THRESHOLD

class MovementComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Component {
        val component = Movement().apply {
            maxSpeed = componentJSON.getFloat("maxSpeed", DEFAULT_MAX_SPEED)
            accelerationFactor = componentJSON.getFloat("acceleration", DEFAULT_ACCELERATION_FACTOR)
            decelerationFactor = componentJSON.getFloat("deceleration", DEFAULT_DECELERATION_FACTOR)
            mass = componentJSON.getFloat("mass", DEFAULT_MASS)
            standingStillThreshold = componentJSON.getFloat("standingStillThreshold", DEFAULT_STANDING_STILL_THRESHOLD)
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