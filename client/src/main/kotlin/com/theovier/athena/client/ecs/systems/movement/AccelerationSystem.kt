package com.theovier.athena.client.ecs.systems.movement

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.movement.*
import ktx.ashley.allOf
import ktx.math.plus
import ktx.math.times

class AccelerationSystem : IteratingSystem(allOf(Velocity::class, Acceleration::class, Direction::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val velocity = entity.velocity
        val acceleration = entity.acceleration
        val direction = entity.direction
        acceleration.acceleration.set(direction.direction * acceleration.accelerationFactor)

        //hack so we do not cap velocity anymore. Velocity can be set to a higher value elsewhere, e.g., in the dash system
        val desiredVelocity = velocity.velocity + acceleration.acceleration * deltaTime
        if (desiredVelocity.len() < velocity.maxSpeed) {
            velocity.velocity = desiredVelocity
        }
    }
}