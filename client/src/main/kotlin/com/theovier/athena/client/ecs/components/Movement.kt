package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.mapperFor

class Movement : Component, Poolable {
    var speed = 0 //units per second

    override fun reset() {
        speed = 0
    }

    companion object {
        val MAPPER = mapperFor<Movement>()
    }
}

val Entity.movement: Movement
    get() = this[Movement.MAPPER] ?: throw GdxRuntimeException("Movement component for entity '$this' is null")