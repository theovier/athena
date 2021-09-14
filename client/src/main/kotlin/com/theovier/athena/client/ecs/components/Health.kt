package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.theovier.athena.client.weapons.Damage
import ktx.ashley.get
import ktx.ashley.has
import ktx.ashley.mapperFor
import java.util.*

class Health : Component {
    var maximum = DEFAULT_MAX_HEALTH
    var current = maximum
    val pendingDamage: Queue<Damage> = LinkedList()

    fun onHit(damage: Damage) {
        pendingDamage.add(damage)
    }

    companion object {
        val MAPPER = mapperFor<Health>()
        const val DEFAULT_MAX_HEALTH = 10
    }
}

val Entity.health: Health
    get() = this[Health.MAPPER] ?: throw GdxRuntimeException("Health for entity '$this' is null")

val Entity.hasHealthComponent: Boolean
    get() = this.has(Health.MAPPER)