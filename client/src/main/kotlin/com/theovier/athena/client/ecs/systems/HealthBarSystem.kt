package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.Health
import com.theovier.athena.client.ecs.components.HealthBar
import com.theovier.athena.client.ecs.components.Transform
import ktx.ashley.allOf

class HealthBarSystem : IteratingSystem(allOf(HealthBar::class, Health::class, Transform::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {

    }
}