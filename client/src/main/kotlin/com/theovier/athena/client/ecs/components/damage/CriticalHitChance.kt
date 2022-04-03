package com.theovier.athena.client.ecs.components.damage

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.has
import ktx.ashley.mapperFor

class CriticalHitChance : Component {
    var chance = DEFAULT_CRIT_CHANCE

    companion object {
        val MAPPER = mapperFor<CriticalHitChance>()
        const val DEFAULT_CRIT_CHANCE = 0.1f
    }
}

val Entity.criticalHitChance: CriticalHitChance
    get() = this[CriticalHitChance.MAPPER] ?: throw GdxRuntimeException("CriticalHitChance component for entity '$this' is null")

val Entity.hasCriticalHitChance: Boolean
    get() = this.has(CriticalHitChance.MAPPER)