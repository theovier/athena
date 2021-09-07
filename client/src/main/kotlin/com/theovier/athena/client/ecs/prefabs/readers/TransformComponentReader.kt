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
            val position = readVector3(positionJSON)
            component.position.set(position)
        }
        if (componentJSON.has(SIZE)) {
            val sizeJSON = componentJSON.get(SIZE)
            val size = readVector2(sizeJSON)
            component.size.set(size)
        }
        component.rotation = componentJSON.getFloat("rotation", 0f)
        return component
    }

    companion object {
        const val POSITION = "position"
        const val SIZE = "size"
    }
}