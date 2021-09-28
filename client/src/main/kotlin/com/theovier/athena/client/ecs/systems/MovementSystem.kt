package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf
import ktx.ashley.exclude
import ktx.math.plus
import ktx.math.times

class MovementSystem : IteratingSystem(allOf(Movement::class, Transform::class).exclude(Physics::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val movement = entity.movement
        if (movement.hasMovementInput) {
            movement.updateVelocity(deltaTime) //todo should be done by a different system?
            transform.position.set(transform.position + movement.velocity * deltaTime)
        }
    }
}