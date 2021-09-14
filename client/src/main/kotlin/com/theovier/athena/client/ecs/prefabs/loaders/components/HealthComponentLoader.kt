package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Health

class HealthComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Health {
        return Health().apply {
            maximum = componentJSON.getInt("maximum", Health.DEFAULT_MAX_HEALTH)
            current = componentJSON.getInt("current", maximum)
        }
    }
}