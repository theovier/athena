package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Player
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class PlayerComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Player {
        return Player()
    }
}