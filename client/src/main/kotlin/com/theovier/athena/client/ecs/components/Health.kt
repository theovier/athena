package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.mapperFor

class Health : Component {
    var maximum = DEFAULT_MAX_HEALTH
    var current = maximum

    companion object {
        val MAPPER = mapperFor<Health>()
        const val DEFAULT_MAX_HEALTH = 10
    }
}

val Entity.health: Health
    get() = this[Health.MAPPER] ?: throw GdxRuntimeException("Health for entity '$this' is null")
