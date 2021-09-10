package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import kotlinx.serialization.Serializable
import ktx.ashley.get
import ktx.ashley.mapperFor

@Serializable
class Lifetime : Component, Poolable {
    var duration = DEFAULT_DURATION

    override fun reset() {
        duration = DEFAULT_DURATION
    }

    companion object {
        val MAPPER = mapperFor<Lifetime>()
        const val DEFAULT_DURATION = 0f
    }
}

val Entity.lifetime: Lifetime
    get() = this[Lifetime.MAPPER] ?: throw GdxRuntimeException("LifeTime for entity '$this' is null")