package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.mapperFor

data class Input(
    val movement: Vector2 = Vector2(0f, 0f),
    var fire: Boolean = false
) : Component {
    companion object {
        val MAPPER = mapperFor<Input>()
    }
}

val Entity.input: Input
    get() = this[Input.MAPPER] ?: throw GdxRuntimeException("Input for entity '$this' is null")
