package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.movement.Velocity

class VelocityComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Velocity {
        return Velocity().apply {
            velocity.x = componentJSON.getFloat(VELOCITY_X, Velocity.DEFAULT_VELOCITY_X)
            velocity.y = componentJSON.getFloat(VELOCITY_Y, Velocity.DEFAULT_VELOCITY_Y)
            maxSpeed = componentJSON.getFloat(MAX_SPEED, Velocity.DEFAULT_MAX_SPEED)
            standingStillThreshold = componentJSON.getFloat(STANDING_STILL_THRESHOLD, Velocity.DEFAULT_STANDING_STILL_THRESHOLD)
        }
    }

    companion object {
        const val VELOCITY_X = "x"
        const val VELOCITY_Y = "y"
        const val MAX_SPEED = "maxSpeed"
        const val STANDING_STILL_THRESHOLD = "standingStillThreshold"
    }
}