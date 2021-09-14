package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.Health
import com.theovier.athena.client.ecs.components.health
import ktx.ashley.allOf

class HealthSystem : IteratingSystem(allOf(Health::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (entity.health.current <= 0) {
            //todo add DeadComponent to play death animation etc. before removing?
            engine.removeEntity(entity)
        }
    }
}