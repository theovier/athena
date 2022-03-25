package com.theovier.athena.client.ecs.systems.damage

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.weapons.Damage
import ktx.ashley.allOf
import ktx.ashley.exclude

class DamageOverTimeSystem : IteratingSystem(allOf(DamageOverTime::class, Health::class).get()) {
    override fun processEntity(victim: Entity, deltaTime: Float) {
        val dot = victim.dot

        dot.duration -= deltaTime
        if (dot.isExpired) {
            victim.remove(DamageOverTime::class.java)
            return
        }

        dot.nextTick -= deltaTime
        if (dot.shouldTick) {
            tick(victim, dot.damage)
            dot.nextTick = dot.tickRate
        }
    }

    private fun tick(victim: Entity, damage: Damage) {
        if (victim.hasNoHitMarkerComponent) {
            victim.add(HitMarker())
        }
        val hitmarker = victim.hitmarker
        hitmarker.onHit(damage)
    }
}