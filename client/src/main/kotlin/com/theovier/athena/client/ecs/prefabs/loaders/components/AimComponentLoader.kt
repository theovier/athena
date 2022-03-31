package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Aim
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class AimComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Aim {
        return Aim()
    }
}