package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
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
        TODO()
    }

    @Test
    @DisplayName("Entity does not move when the maximum speed is zero")
    fun doesNotMoveWhenMaxSpeedIsZero() {
        TODO()
    }

    @Test
    @DisplayName("Entity does not move when its movement direction is zero")
    fun doesNotMoveWhenDirectionIsZero() {
        TODO()
    }

    @Test
    @DisplayName("Entity moves when movement direction / maxSpeed / accelerationFactor are not zero")
    fun doesMoveWhenDirectionAndMaxSpeedAndAccelerationFactorAreNonZero() {
        TODO()
    }
}