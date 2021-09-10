package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Physics
import ktx.box2d.body
import ktx.box2d.box

class PhysicsComponentLoader(private val world: World) : ComponentLoader {

    override fun load(componentJSON: JsonValue): Physics {
        val bodyJSON = componentJSON.get("body")
        val type: BodyDef.BodyType = when(bodyJSON.getString("type")) {
            "kinematic" -> BodyDef.BodyType.KinematicBody
            "dynamic" -> BodyDef.BodyType.DynamicBody
            else -> BodyDef.BodyType.StaticBody
        }
        val position = ComponentLoader.readVector2(bodyJSON.get("position"))

        //todo improve this further for more shapes, fixtures, etc.

        val boxJSON = bodyJSON.get("box")
        val width = boxJSON.getFloat("width", 1.0f)
        val height = boxJSON.getFloat("height", 1.0f)

        return Physics().apply {
            body = world.body(type) {
                box(width = width, height = height)
                this.position.set(position)
            }
        }
    }
}