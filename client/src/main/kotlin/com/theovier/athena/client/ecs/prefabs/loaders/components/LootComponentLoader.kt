package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Loot
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class LootComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Loot {
        return Loot()
    }
}