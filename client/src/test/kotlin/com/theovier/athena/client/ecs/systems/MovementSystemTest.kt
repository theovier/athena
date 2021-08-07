package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.theovier.athena.client.ecs.components.Movement
import com.theovier.athena.client.ecs.components.Transform
import ktx.ashley.entity
import ktx.ashley.get
import ktx.ashley.with
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MovementSystemTest {

    companion object {
        const val DELTA_TIME = 0.1f
    }

    @Test
    @DisplayName("Entity does not move when the acceleration factor is zero")
    fun doesNotMoveWhenAccelerationFactorIsZero() {
        val engine = Engine().apply {
            addSystem(MovementSystem())
        }

        val startingPosition = Vector3(0f, 0f, 0f)
        val entity = engine.entity {
            with<Transform> {
                position.set(startingPosition)
            }
            with<Movement> {
                maxSpeed = 10f
                accelerationFactor = 0f
                direction = Vector2.X
            }
        }
        engine.update(DELTA_TIME)
        val transform = entity[Transform.MAPPER]!!
        Assertions.assertTrue(transform.position == startingPosition)
    }

    @Test
    @DisplayName("Entity does not move when the maximum speed is zero")
    fun doesNotMoveWhenMaxSpeedIsZero() {
        val engine = Engine().apply {
            addSystem(MovementSystem())
        }

        val startingPosition = Vector3(0f, 0f, 0f)
        val entity = engine.entity {
            with<Transform> {
                position.set(startingPosition)
            }
            with<Movement> {
                maxSpeed = 0f
                accelerationFactor = 10f
                direction = Vector2.X
            }
        }
        engine.update(DELTA_TIME)
        val transform = entity[Transform.MAPPER]!!
        Assertions.assertTrue(transform.position == startingPosition)
    }

    @Test
    @DisplayName("Entity does not move when its movement direction is zero")
    fun doesNotMoveWhenDirectionIsZero() {
        val engine = Engine().apply {
            addSystem(MovementSystem())
        }

        val startingPosition = Vector3(0f, 0f, 0f)
        val entity = engine.entity {
            with<Transform> {
                position.set(startingPosition)
            }
            with<Movement> {
                maxSpeed = 10f
                accelerationFactor = 10f
                direction = Vector2.Zero
            }
        }
        engine.update(DELTA_TIME)
        val transform = entity[Transform.MAPPER]!!
        Assertions.assertTrue(transform.position == startingPosition)
    }

    @Test
    @DisplayName("Entity moves when movement direction / maxSpeed / accelerationFactor are not zero")
    fun doesMoveWhenDirectionAndMaxSpeedAndAccelerationFactorAreNonZero() {
        val engine = Engine().apply {
            addSystem(MovementSystem())
        }

        val startingPosition = Vector3(0f, 0f, 0f)
        val entity = engine.entity {
            with<Transform> {
                position.set(startingPosition)
            }
            with<Movement> {
                maxSpeed = 10f
                accelerationFactor = 10f
                direction = Vector2.X
            }
        }
        engine.update(DELTA_TIME)
        val transform = entity[Transform.MAPPER]!!
        Assertions.assertTrue(transform.position != startingPosition)
    }
}