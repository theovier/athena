package com.theovier.athena.client.math

import com.badlogic.gdx.math.MathUtils
import mu.KotlinLogging
import kotlin.math.cos
import kotlin.math.pow
import kotlin.random.Random

/*
non-optimized implementation of 1D perlin noise based upon
https://www.cs.umd.edu/class/fall2018/cmsc425/Lects/lect14-perlin.pdf
http://web.archive.org/web/20160530124230/http://freespace.virgin.net/hugo.elias/models/m_perlin.htm
*/
class PerlinNoiseGenerator(seed: Int, private val boundary: Int = 10) {
    private var random = Random(seed)

    private val noise = DoubleArray(boundary) {
        random.nextDouble()
    }

    /**
     * sum together noise function but using different frequencies with different amplitudes
     * persistence specifies how rapidly the amplitudes decrease (value between 0 and 1)
     */
    fun perlin(x: Double, persistence: Double = 0.5, numberOfOctaves: Int = 8): Double {
        var total = 0.0
        var sumAmplitudes = 0.0 // used for normalizing results to 0.0 - 1.0
        for (i in 0 until numberOfOctaves) {
            val amplitude = persistence.pow(i) // height of the crests
            val frequency = 2.0.pow(i) // frequency (number of crests per unit distance) doubles per octave
            val octave = amplitude * noise(x * frequency)
            total += octave
            sumAmplitudes += amplitude
        }
        return total / sumAmplitudes
    }

    private fun noise(t: Double): Double {
        val x = t.toInt()
        val x0 = x % boundary
        val x1 = if (x0 == boundary - 1) 0 else x0 + 1
        val between = t - x
        
        val y0 = noise[x0]
        val y1 = noise[x1]
        return lerp(y0, y1, between)
    }

    private fun lerp(a: Double, b: Double, alpha: Double): Double {
        return a + alpha * (b - a)
    }

    private fun cosineInterpolation(x0: Double, x1: Double, alpha: Double): Double {
        val g = (1 - cos(alpha * Math.PI)) * 0.5f
        return x0 * (1 - g) + x1 * g
    }

    //https://en.wikipedia.org/wiki/Smoothstep
    private fun smootherstep(x0: Double, x1: Double, alpha: Double): Double {
        val x = MathUtils.clamp((alpha - x0) / (x1 - x0), 0.0, 1.0)
        return x * x * x * (x * (x * 6 - 15) + 10);
    }
}

private val log = KotlinLogging.logger {}
fun main() {
    val generator = PerlinNoiseGenerator(1)

    for (i in 0 until 101) {
        log.debug { generator.perlin(0.1 * i) }
    }

}