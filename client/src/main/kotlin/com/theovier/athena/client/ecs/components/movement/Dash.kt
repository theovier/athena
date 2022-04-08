package com.theovier.athena.client.ecs.components.movement

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor

class Dash : Component, Pool.Poolable {
    var isCurrentlyDashing = false
    var speed = DEFAULT_SPEED
    var timeLeft = DEFAULT_DURATION
    var duration = DEFAULT_DURATION
    var timeBetweenDashes = DEFAULT_TIME_BETWEEN_DASHES
    var canNextDashInSeconds = DEFAULT_CAN_DASH_IN_SECONDS
    val canDash: Boolean
        get() = !isCurrentlyDashing && canNextDashInSeconds <= 0
    val finishedDashing: Boolean
        get() = timeLeft <= 0
    var prefabToSpawn: String? = null
    var trauma = DEFAULT_DASH_TRAUMA

    override fun reset() {
        isCurrentlyDashing = false
        speed = DEFAULT_SPEED
        timeLeft = DEFAULT_DURATION
        duration = DEFAULT_DURATION
        timeBetweenDashes = DEFAULT_TIME_BETWEEN_DASHES
        canNextDashInSeconds = DEFAULT_CAN_DASH_IN_SECONDS
        prefabToSpawn = null
    }

    companion object {
        val MAPPER = mapperFor<Dash>()
        const val DEFAULT_SPEED = 30f
        const val DEFAULT_DURATION = 0.1f
        const val DEFAULT_TIME_LEFT = DEFAULT_DURATION
        const val DEFAULT_TIME_BETWEEN_DASHES = 0.75f
        const val DEFAULT_CAN_DASH_IN_SECONDS = 0f
        const val DEFAULT_DASH_TRAUMA = 0.1f
    }
}

val Entity.dash: Dash
    get() = this[Dash.MAPPER] ?: throw GdxRuntimeException("Dash for entity '$this' is null")