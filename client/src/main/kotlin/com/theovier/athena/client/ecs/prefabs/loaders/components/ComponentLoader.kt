package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

interface ComponentLoader {
    fun load(componentJSON: JsonValue, dependencyPool: DependencyPool = DependencyPool()): Component

    companion object {
        fun readVector2(node: JsonValue, default: Vector2 = Vector2.Zero): Vector2 {
            val x = node.getFloat("x", default.x)
            val y = node.getFloat("y", default.y)
            return Vector2(x, y)
        }

        fun readVector3(node: JsonValue, default: Vector3 = Vector3.Zero): Vector3 {
            val x = node.getFloat("x", default.x)
            val y = node.getFloat("y", default.y)
            val z = node.getFloat("z", default.z)
            return Vector3(x, y, z)
        }
    }
}