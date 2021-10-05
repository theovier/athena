package com.theovier.athena.client.ecs.prefabs.loaders

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.JsonValue

interface EntityLoader {
    fun loadFromFile(fileName: String, configure: Entity.() -> Unit = {}) : Entity
    fun load(entityRoot: JsonValue, configure: Entity.() -> Unit = {}) : Entity
}