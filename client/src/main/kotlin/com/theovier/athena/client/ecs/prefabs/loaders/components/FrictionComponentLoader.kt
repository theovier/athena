package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.movement.Friction

class FrictionComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Component {
        return Friction().apply {
            factor = componentJSON.getFloat(FACTOR, Friction.DEFAULT_DECELERATION_FACTOR)
        }
    }

    companion object {
        const val FACTOR = "factor"
    }
}