package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.mapperFor

class Timer : Component, Poolable {
    var millisSinceStart = DEFAULT_MILLIS_SINCE_START

    override fun reset() {
        millisSinceStart = DEFAULT_MILLIS_SINCE_START
    }

    companion object {
        val MAPPER = mapperFor<Timer>()
        const val DEFAULT_MILLIS_SINCE_START = 0f
    }
}

val Entity.timer: Timer
    get() = this[Timer.MAPPER] ?: throw GdxRuntimeException("Timer component for entity '$this' is null")