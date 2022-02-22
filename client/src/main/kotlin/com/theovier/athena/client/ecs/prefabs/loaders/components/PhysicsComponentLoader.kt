package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Physics
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool
import ktx.box2d.body
import ktx.box2d.box

class PhysicsComponentLoader(private val world: World) : ComponentLoader {

    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Physics {
        val bodyJSON = componentJSON.get("body")

        val type: BodyDef.BodyType = when(bodyJSON.getString("type", "static")) {
            "kinematic" -> BodyDef.BodyType.KinematicBody
            "dynamic" -> BodyDef.BodyType.DynamicBody
            else -> BodyDef.BodyType.StaticBody
        }

        var position: Vector2 = Vector2.Zero
        if (bodyJSON.has("position")) {
            position = ComponentLoader.readVector2(bodyJSON.get("position"))
        }

        val boxJSON = bodyJSON.get("box")
        val width = boxJSON.getFloat("width", 1.0f)
        val height = boxJSON.getFloat("height", 1.0f)
        val isSensor = bodyJSON.has("sensor")

        return Physics().apply {
            body = world.body(type) {
                box(width = width, height = height) {
                    this.isSensor = isSensor
                }
                this.position.set(position)
            }
        }
    }
}