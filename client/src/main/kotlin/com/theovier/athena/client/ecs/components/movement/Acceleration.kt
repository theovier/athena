package com.theovier.athena.client.ecs.components.movement

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.mapperFor

class Acceleration : Component, Poolable {
    var acceleration = Vector2(DEFAULT_ACCELERATION_X, DEFAULT_ACCELERATION_Y)
    var accelerationFactor = DEFAULT_ACCELERATION_FACTOR

    override fun reset() {
        acceleration.set(DEFAULT_ACCELERATION_X, DEFAULT_ACCELERATION_Y)
        accelerationFactor = DEFAULT_ACCELERATION_FACTOR
    }

    companion object {
        val MAPPER = mapperFor<Acceleration>()
        const val DEFAULT_ACCELERATION_X = 0f
        const val DEFAULT_ACCELERATION_Y = 0f
        const val DEFAULT_ACCELERATION_FACTOR = 1f
    }
}

val Entity.acceleration: Acceleration
    get() = this[Acceleration.MAPPER] ?: throw GdxRuntimeException("Acceleration component for entity '$this' is null")