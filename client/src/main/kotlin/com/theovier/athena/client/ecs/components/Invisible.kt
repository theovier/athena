package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.has
import ktx.ashley.mapperFor

class Invisible : Component, Poolable {
    override fun reset() = Unit
    companion object {
        val MAPPER = mapperFor<Invisible>()
    }
}

val Entity.isInvisible: Boolean
    get() = this.has(Invisible.MAPPER)

val Entity.isVisible: Boolean
    get() = !isInvisible