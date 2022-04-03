package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.render.Foreground
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class ForegroundComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Foreground {
        return Foreground()
    }
}