package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import com.theovier.athena.client.ecs.prefabs.serializers.Vector2Serializer
import com.theovier.athena.client.math.clampMagnitude
import kotlinx.serialization.Serializable
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.math.plus
import ktx.math.times
import ktx.math.unaryMinus
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Components are usually considered to carry only the raw data.
 * This component uses some public convenience functions which, however, do only
 * carry out basic modifications of its internal members and no complex business logic.
 */
@Serializable
class Movement : Component, Poolable {

    @XmlElement(true)
    @XmlSerialName("Velocity", "", "")
    @Serializable(with = Vector2Serializer::class)
    var velocity: Vector2 = Vector2()
        set(vector) {
            //always when setting the velocity cap it to the max speed
            field = clampMagnitude(vector, maxSpeed)
        }
    var maxSpeed = 0f //units per second

    @XmlElement(true)
    @XmlSerialName("Acceleration", "", "")
    @Serializable(with = Vector2Serializer::class)
    var acceleration = Vector2()

    var accelerationFactor = 0f
    var decelerationFactor = 0f
    var standingStillThreshold = 0.1f //when should the movement be considered standing still
    var mass = 1f
    private val friction: Vector2
        get() = -velocity * decelerationFactor
    val isNearlyStandingStill: Boolean
        get() = velocity.len2() <= standingStillThreshold

    @XmlElement(true)
    @XmlSerialName("Direction", "", "")
    @Serializable(with = Vector2Serializer::class)
    var direction = Vector2()

    val hasNoMovementInput: Boolean
        get() = direction.isZero
    val hasMovementInput: Boolean
        get() = !hasNoMovementInput

    override fun reset() {
        velocity = Vector2()
        maxSpeed = 0f
        acceleration = Vector2()
        accelerationFactor = 0f
        decelerationFactor = 0f
        mass = 1f
        direction = Vector2()
    }

    fun updateVelocity(deltaTime: Float) {
        resetAcceleration()
        accelerate()
        applyFriction()
        velocity += acceleration * deltaTime
        if (hasNoMovementInput && isNearlyStandingStill) {
            stop()
        }
    }

    private fun applyForce(force: Vector2) {
        acceleration += force * (1 / mass)
    }

    private fun applyFriction() {
        applyForce(friction)
    }

    private fun accelerate() {
        applyForce(direction * accelerationFactor)
    }

    private fun resetAcceleration() {
        acceleration = Vector2.Zero
    }

    private fun stop() {
        velocity = Vector2.Zero
    }

    companion object {
        val MAPPER = mapperFor<Movement>()
    }
}

val Entity.movement: Movement
    get() = this[Movement.MAPPER] ?: throw GdxRuntimeException("Movement component for entity '$this' is null")