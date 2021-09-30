package com.theovier.athena.client.ecs.systems.movement

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.movement.*
import ktx.ashley.allOf
import ktx.math.plus
import ktx.math.times
import ktx.math.unaryMinus

class FrictionSystem : IteratingSystem(allOf(Velocity::class, Friction::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val velocity = entity.velocity
        val friction = entity.friction
        val frictionForce = -velocity.velocity * friction.factor
        velocity.velocity += frictionForce * deltaTime
    }
}