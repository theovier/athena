package com.theovier.athena.client.ecs.systems.movement

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.Physics
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.movement.Velocity
import com.theovier.athena.client.ecs.components.movement.velocity
import com.theovier.athena.client.ecs.components.transform
import ktx.ashley.allOf
import ktx.ashley.exclude
import ktx.math.plus
import ktx.math.times

class MovementSystem : IteratingSystem(allOf(Transform::class, Velocity::class).exclude(Physics::class).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val velocity = entity.velocity
        if (!velocity.isNearlyStandingStill) {
            transform.position.set(transform.position + velocity.velocity * deltaTime)
        }
    }
}