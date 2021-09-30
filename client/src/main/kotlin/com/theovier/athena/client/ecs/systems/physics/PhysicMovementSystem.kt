package com.theovier.athena.client.ecs.systems.physics

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.movement.Velocity
import com.theovier.athena.client.ecs.components.movement.velocity
import ktx.ashley.allOf

class PhysicMovementSystem : IteratingSystem(allOf(Velocity::class, Physics::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val body = entity.physics.body
        val velocity = entity.velocity
        body.linearVelocity = velocity.velocity
    }
}