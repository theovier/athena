package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.mapperFor

class Animation : Component {
    var looping = true
    var name = DEFAULT_NAME

    companion object {
        val MAPPER = mapperFor<Animation>()
        const val DEFAULT_NAME = "idle"
    }
}

val Entity.animation: Animation
    get() = this[Animation.MAPPER] ?: throw GdxRuntimeException("Animation component for entity '$this' is null")