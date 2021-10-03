package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.mapperFor

class HealthBar : Component {
    var fill: Entity? = null
    lateinit var healthReference: Health

    companion object {
        val MAPPER = mapperFor<HealthBar>()
    }
}

val Entity.healthBar: HealthBar
    get() = this[HealthBar.MAPPER] ?: throw GdxRuntimeException("HealthBar for entity '$this' is null")