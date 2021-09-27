package com.theovier.athena.client.ecs.components

import com.badlogic.gdx.math.Vector2

data class Input(
    val movement: Vector2 = Vector2(0f, 0f),
    var fire: Boolean = false
)