package com.theovier.athena.client.ecs.prefabs.readers

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.CameraTarget

class CameraTargetComponentReader : ComponentReader {
    override fun read(componentJSON: JsonValue): Component {
        return CameraTarget()
    }
}