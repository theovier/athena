package com.theovier.athena.client.math

import com.badlogic.gdx.math.Vector2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class Vector2Test {

    @Test
    @DisplayName("Assert that clampMagnitude works with Vector2.Zero")
    fun clampMagnitudeDoesNotDivideByZero() {
        val maxLength = 1f
        val vector = Vector2.Zero
        vector.clampMagnitude(maxLength)
        Assertions.assertTrue(vector.len() <= maxLength)
    }

    @Test
    fun clampMagnitudeTest1() {
        val maxLength = 1f
        val vector = Vector2(0.5f, 0.5f)
        vector.clampMagnitude(maxLength)
        Assertions.assertTrue(vector.len() <= maxLength)
    }

    @Test
    fun clampMagnitudeTest2() {
        val maxLength = 0.5f
        val vector = Vector2(0.5f, 0.5f)
        vector.clampMagnitude(maxLength)
        Assertions.assertTrue(vector.len() <= maxLength)
    }

    @Test
    fun clampMagnitudeStaticTest() {
        val maxLength = 0.5f
        val vector = Vector2(0.5f, 0.5f)
        val clampedVector = clampMagnitude(vector, maxLength)
        Assertions.assertTrue(clampedVector.len() <= maxLength)
    }
}