package com.theovier.athena.client.ecs.systems.loot

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf

class LootRemovalSystem : IteratingSystem(allOf(Loot::class, Looted::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        engine.removeEntity(entity)
    }
}