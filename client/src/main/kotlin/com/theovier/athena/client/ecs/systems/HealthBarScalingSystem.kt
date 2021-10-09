package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf

class HealthBarScalingSystem : IteratingSystem(allOf(HealthBar::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val healthBar = entity.healthBar
        val health = healthBar.healthReference
        val filler = healthBar.fillReference
        filler?.transform?.size?.x = health.currentPercentage * 2f //todo 2f because the initial size was 2
    }
}