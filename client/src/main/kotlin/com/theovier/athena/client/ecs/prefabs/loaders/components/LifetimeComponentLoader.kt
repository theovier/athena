package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Lifetime
import com.theovier.athena.client.ecs.components.Lifetime.Companion.DEFAULT_DURATION
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class LifetimeComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Lifetime {
        return Lifetime().apply {
            duration = componentJSON.getFloat("duration", DEFAULT_DURATION)
        }
    }
}