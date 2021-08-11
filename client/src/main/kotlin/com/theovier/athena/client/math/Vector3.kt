package com.theovier.athena.client.math

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

val Vector3.xy: Vector2
    get() = Vector2(this.x, this.y)

