package com.theovier.athena.client.ecs.components

import com.badlogic.gdx.math.Vector2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MovementTest {
    companion object {
        const val DELTA_TIME = 0.1f
    }

    @Test
    @DisplayName("Is Stopping when nearly standing still")
    fun isStoppingWhenNearlyStandingStill() {
        val movement = Movement()
        val velocity = Vector2(5f, 0f)
        movement.velocity = velocity
        movement.standingStillThreshold = velocity.len2()
        movement.updateVelocity(DELTA_TIME)
        Assertions.assertTrue(movement.isNearlyStandingStill)
        Assertions.assertTrue(movement.velocity == Vector2.Zero)
    }
}