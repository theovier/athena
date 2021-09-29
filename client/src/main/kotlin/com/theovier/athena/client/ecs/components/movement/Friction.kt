package com.theovier.athena.client.ecs.components.movement

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.mapperFor

class Friction : Component, Poolable {
    var factor = DEFAULT_DECELERATION_FACTOR

    override fun reset() {
        factor = DEFAULT_DECELERATION_FACTOR
    }

    companion object {
        val MAPPER = mapperFor<Friction>()
        const val DEFAULT_DECELERATION_FACTOR = 1f
    }
}

val Entity.friction: Friction
    get() = this[Friction.MAPPER] ?: throw GdxRuntimeException("Friction component for entity '$this' is null")