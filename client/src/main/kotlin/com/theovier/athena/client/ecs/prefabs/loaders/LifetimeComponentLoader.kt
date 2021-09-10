package com.theovier.athena.client.ecs.prefabs.loaders

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Lifetime

class LifetimeComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Lifetime {
        return Lifetime().apply {
            duration = componentJSON.getFloat("duration")
        }
    }
}