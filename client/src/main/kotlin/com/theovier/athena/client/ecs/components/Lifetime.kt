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
    var duration = 0f

    override fun reset() {
        duration = 0f
    }

    companion object {
        val MAPPER = mapperFor<Lifetime>()
    }
}

val Entity.lifetime: Lifetime
    get() = this[Lifetime.MAPPER] ?: throw GdxRuntimeException("LifeTime for entity '$this' is null")