package com.theovier.athena.client.ecs.components.aim

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.mapperFor

class Crosshair : Component, Poolable {
    var maxDistanceToPlayer: Float = DEFAULT_MAX_DISTANCE_TO_PLAYER
    lateinit var owner: Entity //todo can be removed if the local/world transform work + crosshair is child of player

    override fun reset() = Unit

    companion object {
        val MAPPER = mapperFor<Crosshair>()
        const val DEFAULT_MAX_DISTANCE_TO_PLAYER = 4f
        const val STANDING_STILL_THRESHOLD = 0.1f
    }
}

val Entity.crosshair: Crosshair
    get() = this[Crosshair.MAPPER] ?: throw GdxRuntimeException("Crosshair component for entity '$this' is null")
