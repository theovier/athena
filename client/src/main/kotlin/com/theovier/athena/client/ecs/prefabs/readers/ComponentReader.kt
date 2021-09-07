package com.theovier.athena.client.ecs.prefabs.readers

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.JsonValue

interface ComponentReader {
    val componentName: String
    fun read(componentJSON: JsonValue): Component

    fun readVector2(node: JsonValue): Vector2 {
        val x = node.getFloat("x", 0f)
        val y = node.getFloat("y", 0f)
        return Vector2(x, y)
    }

    fun readVector3(node: JsonValue): Vector3 {
        val x = node.getFloat("x", 0f)
        val y = node.getFloat("y", 0f)
        val z = node.getFloat("z", 0f)
        return Vector3(x, y, z)
    }
}