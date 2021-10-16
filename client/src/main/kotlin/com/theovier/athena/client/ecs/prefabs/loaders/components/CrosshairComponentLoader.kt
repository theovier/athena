package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Crosshair
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class CrosshairComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Crosshair {
        return Crosshair()
    }
}