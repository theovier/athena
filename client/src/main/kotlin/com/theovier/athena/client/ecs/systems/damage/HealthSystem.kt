package com.theovier.athena.client.ecs.systems.damage

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.weapons.Damage
import com.theovier.athena.client.weapons.DamageSource
import ktx.ashley.allOf
import ktx.ashley.exclude

class HealthSystem : IteratingSystem(allOf(Health::class, HitMarker::class).exclude(Invincible::class).get()) {

    override fun processEntity(victim: Entity, deltaTime: Float) {
        victim.hitmarker.hits.forEach { hit ->
            takeDamage(victim, hit)
        }
    }

    private fun takeDamage(victim: Entity, damage: Damage) {
        val health = victim.health
        health.current -= damage.amount
        if (health.current <= 0) {
            die(victim, damage.source)
        }
    }

    private fun die(victim: Entity, source: DamageSource?) {
        //todo add Dead (JustDied) Component
        engine.removeEntity(victim)
    }
}