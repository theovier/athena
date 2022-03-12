package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Impact
import com.theovier.athena.client.ecs.components.Text
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class ImpactComponentLoader : ComponentLoader {

    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Impact {
        return Impact().apply {
            prefabToSpawn = componentJSON.getString("prefabToSpawn", "")
        }
    }
}