package com.theovier.athena.client.ecs.prefabs.readers

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Transform

class TransformComponentReader : ComponentReader {
    override val componentName = "transform"

    override fun read(componentJSON: JsonValue): Transform {
        val component = Transform()
        if (componentJSON.has(POSITION)) {
            val positionJSON = componentJSON.get(POSITION)
            val position = readPosition(positionJSON)
            component.position.set(position)
        }
        if (componentJSON.has(SIZE)) {
            val sizeJSON = componentJSON.get(SIZE)
            val size = readSize(sizeJSON)
            component.size.set(size)
        }
        component.rotation = componentJSON.getFloat("rotation", 0f)
        return component
    }

    private fun readPosition(node: JsonValue): Vector3 {
        val x = node.getFloat("x", 0f)
        val y = node.getFloat("y", 0f)
        val z = node.getFloat("z", 0f)
        return Vector3(x, y, z)
    }

    private fun readSize(node: JsonValue): Vector2 {
        val x = node.getFloat("x", 0f)
        val y = node.getFloat("y", 0f)
        return Vector2(x, y)
    }

    companion object {
        const val POSITION = "position"
        const val SIZE = "size"
    }
}