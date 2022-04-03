package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.damage.DamageOverTime
import com.theovier.athena.client.ecs.components.damage.Health
import com.theovier.athena.client.ecs.components.damage.hasHitMarkerComponent
import com.theovier.athena.client.ecs.components.damage.hitmarker
import com.theovier.athena.client.ecs.systems.damage.DamageOverTimeSystem
import com.theovier.athena.client.weapons.Damage
import com.theovier.athena.client.weapons.DamageSource
import com.theovier.athena.client.weapons.DamageType
import ktx.ashley.has
import org.junit.Assert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class DamageOverTimeSystemTest {

    companion object {
        const val DELTA_TIME = 0.1f
    }

    @Test
    @DisplayName("Is <HitMarker> applied")
    fun isHitMarkerApplied() {
        val attacker = Entity()
        val victim = Entity()
        val damageSource = DamageSource(attacker, attacker)
        val damage = Damage(1, DamageType.PHYSICAL, damageSource)
        val dot = DamageOverTime(damage).apply {
            duration = 2f
            tickRate = 0.1f
            nextTick = tickRate
        }
        victim.add(Health())
        victim.add(dot)
        val engine = Engine().apply {
            addSystem(DamageOverTimeSystem())
            addEntity(victim)
        }
        engine.update(DELTA_TIME)
        Assertions.assertTrue(victim.hasHitMarkerComponent)
        val hitMarker = victim.hitmarker
        Assertions.assertEquals(hitMarker.hits.size, 1)
        val hit = hitMarker.hits[0]
        Assertions.assertEquals(hit.amount, damage.amount)
        Assertions.assertEquals(hit.source, damageSource)
    }

    @Test
    @DisplayName("Is <DamageOverTime> removed after its Duration")
    fun isDamageOverTimeRemovedAfterItsDuration() {
        val attacker = Entity()
        val victim = Entity()
        val damageSource = DamageSource(attacker, attacker)
        val damage = Damage(1, DamageType.PHYSICAL, damageSource)
        val dot = DamageOverTime(damage).apply {
            duration = 0.05f
        }
        victim.add(Health())
        victim.add(dot)
        val engine = Engine().apply {
            addSystem(DamageOverTimeSystem())
            addEntity(victim)
        }
        engine.update(DELTA_TIME)
        Assertions.assertFalse(victim.has(DamageOverTime.MAPPER))
    }

    //todo: is dot component removed after duration
}