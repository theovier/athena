package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.damage.HealthBar
import com.theovier.athena.client.ecs.components.damage.healthBar
import ktx.ashley.allOf

class HealthBarScalingSystem : IteratingSystem(allOf(HealthBar::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val healthBar = entity.healthBar
        val health = healthBar.healthReference
        val filler = healthBar.fillReference
        val originalWidth = healthBar.fillWidthAtFullLife
        filler?.transform?.size?.x = health.currentPercentage * originalWidth
    }
}