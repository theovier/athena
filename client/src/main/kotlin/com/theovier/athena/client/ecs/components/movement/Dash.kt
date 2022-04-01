package com.theovier.athena.client.ecs.components.movement

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.mapperFor

class Dash : Component {
    var isCurrentlyDashing = false
    var remainingDashes = DEFAULT_REMAINING_DASHES
    var maximumDashes = DEFAULT_MAXIMUM_DASHES
    var speed = DEFAULT_SPEED
    var timeLeft = DEFAULT_DURATION
    var duration = DEFAULT_DURATION
    var timeBetweenDashes = DEFAULT_TIME_BETWEEN_DASHES
    var canNextDashInSeconds = 0f
    val canDash: Boolean
        get() = !isCurrentlyDashing && canNextDashInSeconds <= 0
    val finishedDashing: Boolean
        get() = timeLeft <= 0

    companion object {
        val MAPPER = mapperFor<Dash>()
        const val DEFAULT_MAXIMUM_DASHES = 1
        const val DEFAULT_REMAINING_DASHES = DEFAULT_MAXIMUM_DASHES
        const val DEFAULT_SPEED = 30f
        const val DEFAULT_DURATION = 0.1f
        const val DEFAULT_TIME_BETWEEN_DASHES = 0.75f
    }
}

val Entity.dash: Dash
    get() = this[Dash.MAPPER] ?: throw GdxRuntimeException("Dash for entity '$this' is null")