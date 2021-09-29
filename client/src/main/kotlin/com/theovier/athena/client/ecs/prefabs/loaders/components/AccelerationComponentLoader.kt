package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.movement.Acceleration

class AccelerationComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Component {
        return Acceleration().apply {
            acceleration.x = componentJSON.getFloat(ACCELERATION_X, Acceleration.DEFAULT_ACCELERATION_X)
            acceleration.y = componentJSON.getFloat(ACCELERATION_Y, Acceleration.DEFAULT_ACCELERATION_Y)
            accelerationFactor = componentJSON.getFloat(FACTOR, Acceleration.DEFAULT_ACCELERATION_FACTOR)
        }
    }

    companion object {
        const val ACCELERATION_X = "x"
        const val ACCELERATION_Y = "y"
        const val FACTOR = "factor"
    }
}