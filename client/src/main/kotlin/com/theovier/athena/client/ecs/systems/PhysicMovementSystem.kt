package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf

class PhysicMovementSystem : IteratingSystem(allOf(Movement::class, Physics::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val body = entity.physics.body
        val movement = entity.movement
        movement.updateVelocity(deltaTime)
        body.setLinearVelocity(movement.velocity.x, movement.velocity.y)
    }
}