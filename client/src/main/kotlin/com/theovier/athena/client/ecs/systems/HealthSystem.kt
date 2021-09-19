package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.listeners.damage.DamageEventSource
import com.theovier.athena.client.ecs.listeners.damage.DamageListener
import com.theovier.athena.client.weapons.Damage
import com.theovier.athena.client.weapons.DamageSource
import ktx.ashley.allOf
import ktx.ashley.exclude

class HealthSystem : IteratingSystem(allOf(Health::class).exclude(Invincible::class).get()), DamageEventSource {
    private val listeners = mutableListOf<DamageListener>()

    override fun addDamageListener(listener: DamageListener) {
        listeners += listener
    }

    override fun removeDamageListener(listener: DamageListener) {
        listeners -= listener
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val health = entity.health
        while (health.pendingDamage.isNotEmpty()) {
            val damage = health.pendingDamage.poll()
            takeDamage(entity, damage)
            if (health.current <= 0) {
                die(entity, damage.source)
            }
        }
    }

    private fun takeDamage(victim: Entity, damage: Damage) {
        //this is the place to add resistance/shields/absorbs
        val health = victim.health
        health.current -= damage.amount
        listeners.forEach {
            it.onDamageTaken(victim, damage)
        }
    }

    private fun die(victim: Entity, source: DamageSource?) {
        listeners.forEach {
            it.onDeath(victim, source)
        }
        engine.removeEntity(victim)
    }
}