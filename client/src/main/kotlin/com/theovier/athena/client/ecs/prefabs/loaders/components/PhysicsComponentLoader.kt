package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Physics

class PhysicsComponentLoader(private val world: World) : ComponentLoader {

    override fun load(componentJSON: JsonValue): Physics {
        return Physics()
    }
}