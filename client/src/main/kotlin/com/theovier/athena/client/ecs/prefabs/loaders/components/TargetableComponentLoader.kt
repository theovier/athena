package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Targetable
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class TargetableComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Targetable {
        return Targetable()
    }
}