package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.movement.*
import com.theovier.athena.client.ecs.systems.movement.FrictionSystem
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class FrictionSystemTest {

    companion object {
        const val DELTA_TIME = 0.1f
    }

    @Test
    @DisplayName("Velocity is calculated and set correctly")
    fun isVelocitySetCorrectly() {
        val expectedVelocity = Vector2(0.8f, 0f)
        val engine = Engine().apply {
            addSystem(FrictionSystem())
        }
        val entity = Entity()
        entity.add(Velocity().apply {
            velocity.set(1f, 0f)
        })
        entity.add(Friction().apply {
            factor = 2f
        })
        engine.addEntity(entity)
        engine.update(DELTA_TIME)
        Assertions.assertEquals(expectedVelocity, entity.velocity.velocity)
    }
}