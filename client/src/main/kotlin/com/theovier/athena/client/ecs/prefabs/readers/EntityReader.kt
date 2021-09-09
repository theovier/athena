package com.theovier.athena.client.ecs.prefabs.readers

import com.badlogic.ashley.core.Entity

interface EntityReader {
    fun read(fileName: String, configure: Entity.() -> Unit = {}) : Entity
}