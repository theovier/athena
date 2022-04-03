package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.theovier.athena.client.weapons.Damage
import ktx.ashley.get
import ktx.ashley.mapperFor

class DamageOverTime(val damage: Damage) : Component {
    var duration = DEFAULT_DURATION
    var tickRate = DEFAULT_TICK_RATE
    var nextTick = tickRate

    val isExpired: Boolean
        get() = duration <= 0f
    val shouldTick: Boolean
        get() = nextTick <= 0f

    companion object {
        val MAPPER = mapperFor<DamageOverTime>()
        const val DEFAULT_DURATION = 5f
        const val DEFAULT_TICK_RATE = 1f
    }
}

val Entity.dot: DamageOverTime
    get() = this[DamageOverTime.MAPPER] ?: throw GdxRuntimeException("DamageOverTime for entity '$this' is null")