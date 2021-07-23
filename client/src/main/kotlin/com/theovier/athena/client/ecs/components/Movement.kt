package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import com.theovier.athena.client.math.clampMagnitude
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.math.plus
import ktx.math.times
import ktx.math.unaryMinus

/**
 * Components are usually considered to carry only the raw data.
 * This component uses some public convenience functions which, however, do only
 * carry out basic modifications of its internal members and no complex business logic.
 */
class Movement : Component, Poolable {
    var velocity: Vector2 = Vector2()
        set(vector) {
            //always when setting the velocity cap it to the max speed
            field = clampMagnitude(vector, maxSpeed)
        }
    var maxSpeed = 0f //units per second
    var acceleration = Vector2()
    var accelerationFactor = 0f
    var decelerationFactor = 0f
    private var standingStillThreshold = 0.1 //when should the movement be considered standing still
    var mass = 1f
    val friction: Vector2
        get() = -velocity * decelerationFactor
    val isNearlyStandingStill: Boolean
        get() = velocity.len2() <= standingStillThreshold

    override fun reset() {
        velocity = Vector2()
        maxSpeed = 0f
        acceleration = Vector2()
        accelerationFactor = 0f
        decelerationFactor = 0f
        mass = 1f
    }

    fun applyForce(force: Vector2) {
        acceleration += force * (1 / mass)
    }

    fun applyFriction() {
        applyForce(friction)
    }

    fun resetAcceleration() {
        acceleration = Vector2.Zero
    }

    fun move(deltaTime: Float) {
        velocity += acceleration * deltaTime
    }

    fun stop() {
        velocity = Vector2.Zero
    }

    companion object {
        val MAPPER = mapperFor<Movement>()
    }
}

val Entity.movement: Movement
    get() = this[Movement.MAPPER] ?: throw GdxRuntimeException("Movement component for entity '$this' is null")