package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.mapperFor

class Animation(var name: String) : Component {
    var looping = true

    companion object {
        val MAPPER = mapperFor<Animation>()
    }
}

val Entity.animation: Animation
    get() = this[Animation.MAPPER] ?: throw GdxRuntimeException("Animation component for entity '$this' is null")