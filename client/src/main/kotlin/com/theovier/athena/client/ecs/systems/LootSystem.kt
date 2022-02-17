package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf

class LootSystem : IteratingSystem(allOf(Loot::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val loot = entity.loot

       if (loot.lootedBy != null) {
           engine.removeEntity(entity)
       }
    }
}