package com.theovier.athena.client.ecs.prefabs.readers

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Aim

class AimComponentReader : ComponentReader {
    override fun read(componentJSON: JsonValue): Component {
        return Aim().apply {
            maxDistanceToPlayer = componentJSON.getFloat("maxDistanceToPlayer", 4f)
        }
    }
}