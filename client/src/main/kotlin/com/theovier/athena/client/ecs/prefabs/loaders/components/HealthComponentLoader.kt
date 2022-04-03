package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.damage.Health
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class HealthComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Health {
        return Health().apply {
            maximum = componentJSON.getInt("maximum", Health.DEFAULT_MAX_HEALTH)
            current = componentJSON.getInt("current", maximum)
        }
    }
}