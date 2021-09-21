package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.Health
import com.theovier.athena.client.ecs.components.HitMarker
import com.theovier.athena.client.ecs.components.hitmarker
import com.theovier.athena.client.weapons.Damage
import com.theovier.athena.client.weapons.DamageType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HealthSystemTest {

    companion object {
        const val DELTA_TIME = 0.1f
    }

    @Test
    fun isHealthReduced() {
        val entity = Entity()
        val health = Health().apply {
            maximum = 5
            current = 5
        }
        entity.add(health)
        val engine = Engine().apply {
            addSystem(HealthSystem())
            addEntity(entity)
        }
        Assertions.assertEquals(health.current, health.maximum)
        entity.add(HitMarker())
        entity.hitmarker.onHit(Damage(1, DamageType.PHYSICAL, null))
        engine.update(DELTA_TIME)
        Assertions.assertEquals(health.current, 4)
    }

    @Test
    fun isEntityRemovedOnDeath() {
        val entity = Entity()
        val health = Health().apply {
            maximum = 5
            current = 5
        }
        entity.add(health)
        val engine = Engine().apply {
            addSystem(HealthSystem())
            addEntity(entity)
        }
        Assertions.assertEquals(1, engine.entities.count())
        entity.add(HitMarker())
        entity.hitmarker.onHit(Damage(health.maximum, DamageType.PHYSICAL, null))
        engine.update(DELTA_TIME)
        Assertions.assertEquals(0, engine.entities.count())
    }
}