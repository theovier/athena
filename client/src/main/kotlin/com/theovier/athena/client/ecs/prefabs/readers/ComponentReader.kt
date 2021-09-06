package com.theovier.athena.client.ecs.prefabs.readers

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.JsonValue

interface ComponentReader {
    val componentName: String
    fun read(componentJSON: JsonValue): Component
}