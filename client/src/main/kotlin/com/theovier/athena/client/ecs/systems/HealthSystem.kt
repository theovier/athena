package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.esotericsoftware.spine.attachments.PointAttachment
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.prefabs.Prefab
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
            takeDamage(entity, damage)
            if (health.current <= 0) {
                die(entity, damage.source)
            }
        }
    }

    private fun takeDamage(entity: Entity, damage: Damage) {
        //this is the place to add resistance/shields/absorbs
        val health = entity.health
        health.current -= damage.amount
        spawnDamageIndicator(entity, damage)
    }

    private fun spawnDamageIndicator(entity: Entity, damage: Damage) {
        if (!entity.hasSpineComponent || entity.spine.hasNoDamageIndicator) {
            return
        }
        val spine = entity.spine
        val spawnPosition = spine.getRandomDamageIndicatorPosition()
        val indicator = Prefab.instantiate("damageIndicator") {
            with(transform) {
                position.set(spawnPosition, 0f)
            }
            with(text) {
                text = "${damage.amount}"
            }
        }
        engine.addEntity(indicator)
    }

    private fun die(victim: Entity, source: DamageSource?) {
        //todo add DeadComponent to play death animation etc. before removing the entity entirely
        engine.removeEntity(victim)
    }
}