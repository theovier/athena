package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Spin

class SpinComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Component {
        return Spin().apply {
            speed = componentJSON.getFloat(ROTATION_SPEED, Spin.DEFAULT_ROTATION_SPEED)
        }
    }

    companion object {
        const val ROTATION_SPEED = "speed"
    }
}