package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Spin
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class SpinComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Spin {
        return Spin().apply {
            speed = componentJSON.getFloat(ROTATION_SPEED, Spin.DEFAULT_ROTATION_SPEED)
        }
    }

    companion object {
        const val ROTATION_SPEED = "speed"
    }
}