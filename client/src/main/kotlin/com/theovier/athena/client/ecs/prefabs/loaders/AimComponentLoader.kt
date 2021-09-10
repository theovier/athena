package com.theovier.athena.client.ecs.prefabs.loaders

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Aim
import com.theovier.athena.client.ecs.components.Aim.Companion.DEFAULT_MAX_DISTANCE_TO_PLAYER

class AimComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Component {
        return Aim().apply {
            maxDistanceToPlayer = componentJSON.getFloat("maxDistanceToPlayer", DEFAULT_MAX_DISTANCE_TO_PLAYER)
        }
    }
}