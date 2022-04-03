package com.theovier.athena.client.math

import com.badlogic.gdx.math.MathUtils

//todo write own rng generator: https://www.youtube.com/watch?v=LWFzPP8ZbdU with seeds and stuff
class Randomness {

    companion object {
        fun rollRandomChance(probabilityOfReturningTrue: Float): Boolean {
            return MathUtils.randomBoolean(probabilityOfReturningTrue)
        }
    }
}