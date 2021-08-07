package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf

class LifetimeSystem : IteratingSystem(allOf(Lifetime::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val lifetime = entity.lifetime

        lifetime.duration -= deltaTime
        if (lifetime.duration <= 0) {
            engine.removeEntity(entity)
        }
    }
}