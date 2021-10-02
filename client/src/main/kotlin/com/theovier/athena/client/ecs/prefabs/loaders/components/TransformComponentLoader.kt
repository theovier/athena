package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Transform

class TransformComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Transform {
        val component = Transform()
        if (componentJSON.has(POSITION)) {
            val positionJSON = componentJSON.get(POSITION)
            val position = ComponentLoader.readVector3(positionJSON)
            component.position.set(position)
        }
        if (componentJSON.has(LOCAL_POSITION)) {
            val positionJSON = componentJSON.get(LOCAL_POSITION)
            val position = ComponentLoader.readVector3(positionJSON)
            component.localPosition.set(position)
        }
        if (componentJSON.has(SIZE)) {
            val sizeJSON = componentJSON.get(SIZE)
            val size = ComponentLoader.readVector2(sizeJSON, Vector2(1f, 1f))
            component.size.set(size)
        }
        component.rotation = componentJSON.getFloat("rotation", 0f)
        return component
    }

    companion object {
        const val POSITION = "position"
        const val LOCAL_POSITION = "localPosition"
        const val SIZE = "size"
    }
}