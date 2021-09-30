package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.mapperFor

class Spin : Component {
    var speed = DEFAULT_ROTATION_SPEED

    companion object {
        val MAPPER = mapperFor<Spin>()
        const val DEFAULT_ROTATION_SPEED = 400f
    }
}

val Entity.spin: Spin
    get() = this[Spin.MAPPER] ?: throw GdxRuntimeException("Spin for entity '$this' is null")