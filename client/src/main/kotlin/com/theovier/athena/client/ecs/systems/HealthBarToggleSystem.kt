package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.extensions.InputDrivenIteratingSystem
import ktx.ashley.allOf

class HealthBarToggleSystem : InputDrivenIteratingSystem(allOf(HealthBar::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (input.hideHealthBars) {
            if (entity.isVisible) {
                entity.add(Invisible())
            }
        } else if (entity.isInvisible) {
            entity.remove(Invisible::class.java)
        }
    }
}