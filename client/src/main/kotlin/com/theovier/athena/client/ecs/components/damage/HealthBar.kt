package com.theovier.athena.client.ecs.components.damage

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.mapperFor

class HealthBar : Component {
    lateinit var fillReference: Entity
    lateinit var healthReference: Health
    var fillWidthAtFullLife = FILL_WIDTH_AT_FULL_LIFE

    companion object {
        val MAPPER = mapperFor<HealthBar>()
        const val FILL_WIDTH_AT_FULL_LIFE = 1f
    }
}

val Entity.healthBar: HealthBar
    get() = this[HealthBar.MAPPER] ?: throw GdxRuntimeException("HealthBar for entity '$this' is null")