package com.theovier.athena.client.ecs.prefabs.readers

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Lifetime

class LifetimeComponentReader : ComponentReader {
    override val componentName = "lifetime"

    override fun read(componentJSON: JsonValue): Lifetime {
        return Lifetime().apply {
            duration = componentJSON.getFloat("duration")
        }
    }
}