package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf

class HealthBarSystem : IteratingSystem(allOf(HealthBar::class, Transform::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val healthBar = entity.healthBar
        val health = healthBar.healthReference

        transform.size.x = health.currentPercentage * 2f //todo 2f because the initial size was 2
    }
}