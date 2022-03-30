package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.extensions.input
import ktx.ashley.allOf

class HealthBarToggleSystem : IteratingSystem(allOf(HealthBar::class).get()) {
    private lateinit var input: Input

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        input = engine.input
    }

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