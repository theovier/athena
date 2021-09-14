package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.Health
import com.theovier.athena.client.ecs.components.Invincible
import com.theovier.athena.client.ecs.components.health
import com.theovier.athena.client.weapons.Damage
import com.theovier.athena.client.weapons.DamageSource
import ktx.ashley.allOf
import ktx.ashley.exclude

class HealthSystem : IteratingSystem(allOf(Health::class).exclude(Invincible::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        takeDamage(entity)
    }

    private fun takeDamage(entity: Entity) {
        val health = entity.health
        while (health.pendingDamage.isNotEmpty()) {
            val damage = health.pendingDamage.poll()
            takeDamage(health, damage)
            if (health.current <= 0) {
                die(entity, damage.source)
            }
        }
    }

    private fun takeDamage(health: Health, damage: Damage) {
        //this is the place to add resistance/shields/absorbs
        health.current -= damage.amount
        //todo spawn damage indicator entity
    }

    private fun die(victim: Entity, source: DamageSource?) {
        //todo add DeadComponent to play death animation etc. before removing the entity entirely
        engine.removeEntity(victim)
    }
}