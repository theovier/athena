package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.mapperFor

class Travel : Component, Poolable {
    var origin = Vector2(DEFAULT_ORIGIN_X, DEFAULT_ORIGIN_Y)
    var maxTravelDistance = Vector2(MAX_TRAVEL_DISTANCE_X, MAX_TRAVEL_DISTANCE_Y)

    override fun reset() {
        origin = Vector2(DEFAULT_ORIGIN_X, DEFAULT_ORIGIN_Y)
        maxTravelDistance = Vector2(MAX_TRAVEL_DISTANCE_X, MAX_TRAVEL_DISTANCE_Y)
    }

    companion object {
        val MAPPER = mapperFor<Travel>()
        const val DEFAULT_ORIGIN_X = 0f
        const val DEFAULT_ORIGIN_Y = 0f
        const val MAX_TRAVEL_DISTANCE_X = 1f
        const val MAX_TRAVEL_DISTANCE_Y = 1f
    }
}

val Entity.travel: Travel
    get() = this[Travel.MAPPER] ?: throw GdxRuntimeException("Travel component for entity '$this' is null")