package com.theovier.athena.client.ecs.prefabs.loaders

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Aim

class AimComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Component {
        return Aim().apply {
            maxDistanceToPlayer = componentJSON.getFloat("maxDistanceToPlayer", 4f)
        }
    }
}