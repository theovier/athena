package com.theovier.athena.client.ecs.prefabs.loaders

import com.badlogic.ashley.core.Entity

interface EntityLoader {
    fun load(fileName: String, configure: Entity.() -> Unit = {}) : Entity
}