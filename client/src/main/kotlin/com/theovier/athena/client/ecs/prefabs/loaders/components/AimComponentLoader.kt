package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Aim
import com.theovier.athena.client.ecs.components.Aim.Companion.DEFAULT_MAX_DISTANCE_TO_PLAYER
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class AimComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Aim {
        return Aim().apply {
            maxDistanceToPlayer = componentJSON.getFloat("maxDistanceToPlayer", DEFAULT_MAX_DISTANCE_TO_PLAYER)
        }
    }
}