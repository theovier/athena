package com.theovier.athena.client.math


import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class PerlinNoiseGeneratorTest {

    @Test
    @DisplayName("Perlin Noise at X is always the same for the same seed")
    fun perlinNoiseReturnsAlwaysTheSameForSameSeed() {
        val generator = PerlinNoiseGenerator(1)
        val generator2 = PerlinNoiseGenerator(1)
        val noise = generator.perlin(1.0)
        val noise2 = generator2.perlin(1.0)
        Assertions.assertTrue(noise == noise2)
    }

    @Test
    @DisplayName("Perlin Noise at X is different for different seed")
    fun perlinNoiseReturnsDifferentValueForDifferentSeed() {
        val seed = 1
        val generator = PerlinNoiseGenerator(seed)
        val generator2 = PerlinNoiseGenerator(seed + 1)
        val noise = generator.perlin(1.0)
        val noise2 = generator2.perlin(1.0)
        Assertions.assertTrue(noise != noise2)
    }
}