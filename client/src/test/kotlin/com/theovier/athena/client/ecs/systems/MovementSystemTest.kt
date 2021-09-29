package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.math.Vector3
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.movement.Velocity
import com.theovier.athena.client.ecs.components.transform
import com.theovier.athena.client.ecs.systems.movement.MovementSystem
import ktx.ashley.entity
import ktx.ashley.with
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MovementSystemTest {

    companion object {
        const val DELTA_TIME = 0.1f
    }

    @Test
    @DisplayName("Entity does not move when the velocity is zero")
    fun doesNotMoveWhenVelocityIsZero() {
        val engine = Engine().apply {
            addSystem(MovementSystem())
        }
        val startingPosition = Vector3(0f, 0f, 0f)
        val entity = engine.entity {
            with<Transform> {
                position.set(startingPosition)
            }
            with<Velocity> {
                velocity.set(0f, 0f)
            }
        }
        engine.update(DELTA_TIME)
        Assertions.assertEquals(startingPosition, entity.transform.position)
    }

    @Test
    @DisplayName("Entity moves when velocity is non zero")
    fun doesMoveWhenVelocityIsNonZero() {
        val engine = Engine().apply {
            addSystem(MovementSystem())
        }
        val startingPosition = Vector3(0f, 0f, 0f)
        val entity = engine.entity {
            with<Transform> {
                position.set(startingPosition)
            }
            with<Velocity> {
                velocity.set(1f, 0f)
            }
        }
        engine.update(DELTA_TIME)
        Assertions.assertNotEquals(entity.transform.position, startingPosition)
    }
}