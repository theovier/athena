package com.theovier.athena.client.ecs.components.movement

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import com.theovier.athena.client.math.clampMagnitude
import ktx.ashley.get
import ktx.ashley.mapperFor
import mu.KotlinLogging

class Velocity : Component, Poolable {
    var velocity: Vector2 = Vector2(DEFAULT_VELOCITY_X, DEFAULT_VELOCITY_Y)
    var maxSpeed = DEFAULT_MAX_SPEED //units per second
    var standingStillThreshold = DEFAULT_STANDING_STILL_THRESHOLD //when should the movement be considered standing still
    val isNearlyStandingStill: Boolean
        get() = velocity.len2() <= standingStillThreshold
    val isNearlyStandingStillForAnimation: Boolean
        get() = velocity.len2() <= standingStillThreshold * 2f

    override fun reset() {
        velocity = Vector2(DEFAULT_VELOCITY_X, DEFAULT_VELOCITY_Y)
        maxSpeed = DEFAULT_MAX_SPEED
        standingStillThreshold = DEFAULT_STANDING_STILL_THRESHOLD
    }

    companion object {
        val MAPPER = mapperFor<Velocity>()
        const val DEFAULT_VELOCITY_X = 0f
        const val DEFAULT_VELOCITY_Y = 0f
        const val DEFAULT_MAX_SPEED = 5f
        const val DEFAULT_STANDING_STILL_THRESHOLD = 0.5f
    }
}

val Entity.velocity: Velocity
    get() = this[Velocity.MAPPER] ?: throw GdxRuntimeException("Velocity component for entity '$this' is null")