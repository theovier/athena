package com.theovier.athena.client.ecs.components.movement

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.mapperFor

class Direction : Component, Poolable {
    var direction = Vector2(DEFAULT_DIRECTION_X, DEFAULT_DIRECTION_Y)

    override fun reset() {
        direction = Vector2(DEFAULT_DIRECTION_X, DEFAULT_DIRECTION_Y)
    }

    companion object {
        val MAPPER = mapperFor<Direction>()
        const val DEFAULT_DIRECTION_X = 0f
        const val DEFAULT_DIRECTION_Y = 0f
    }
}

val Entity.direction: Direction
    get() = this[Direction.MAPPER] ?: throw GdxRuntimeException("Direction component for entity '$this' is null")