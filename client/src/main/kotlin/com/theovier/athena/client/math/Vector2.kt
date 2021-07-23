package com.theovier.athena.client.math

import com.badlogic.gdx.math.Vector2
import kotlin.math.min

fun Vector2.clampMagnitude(maxLength: Float) {
    if (this.len() == 0f) return
    val f = min(this.len(), maxLength) / this.len()
    this.set(f * this.x, f * this.y)
}

//workaround as we cannot extend static java methods in kotlin
fun clampMagnitude(vector: Vector2, maxLength: Float): Vector2 {
    vector.clampMagnitude(maxLength)
    return vector
}

