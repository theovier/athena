package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.movement.*
import com.theovier.athena.client.ecs.systems.movement.AccelerationSystem
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AccelerationSystemTest {

    companion object {
        const val DELTA_TIME = 0.1f
    }

    @Test
    @DisplayName("Acceleration is calculated and set correctly")
    fun isAccelerationSetCorrectly() {
        val expectedAcceleration = Vector2(2f, 0f)
        val engine = Engine().apply {
            addSystem(AccelerationSystem())
        }
        val entity = Entity()
        entity.add(Velocity())
        entity.add(Acceleration().apply {
            accelerationFactor = 2f
        })
        entity.add(Direction().apply {
            direction.set(1f, 0f)
        })
        engine.addEntity(entity)
        engine.update(DELTA_TIME)
        Assertions.assertEquals(expectedAcceleration, entity.acceleration.acceleration)
    }

    @Test
    @DisplayName("Acceleration is calculated and set correctly when direction is (0,0)")
    fun isAccelerationSetCorrectlyWhenDirectionIsZero() {
        val expectedAcceleration = Vector2.Zero
        val engine = Engine().apply {
            addSystem(AccelerationSystem())
        }
        val entity = Entity()
        entity.add(Velocity())
        entity.add(Acceleration())
        entity.add(Direction())
        engine.addEntity(entity)
        engine.update(DELTA_TIME)
        Assertions.assertEquals(expectedAcceleration, entity.acceleration.acceleration)
    }

    @Test
    @DisplayName("Velocity is calculated and set correctly")
    fun isVelocitySetCorrectly() {
        val expectedVelocity = Vector2(0.2f, 0f)
        val engine = Engine().apply {
            addSystem(AccelerationSystem())
        }
        val entity = Entity()
        entity.add(Velocity().apply {
            standingStillThreshold = 0.8f
        })
        entity.add(Acceleration().apply {
            accelerationFactor = 2f
        })
        entity.add(Direction().apply {
            direction.set(1f, 0f)
        })
        engine.addEntity(entity)
        engine.update(DELTA_TIME)
        Assertions.assertEquals(expectedVelocity, entity.velocity.velocity)
    }
}