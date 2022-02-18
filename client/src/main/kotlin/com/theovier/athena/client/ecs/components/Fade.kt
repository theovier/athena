package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.mapperFor

class Fade : Component {
    var start = DEFAULT_START
    var end = DEFAULT_END
    var current = DEFAULT_CURRENT
    var duration = DEFAULT_DURATION

    companion object {
        val MAPPER = mapperFor<Fade>()
        const val DEFAULT_START = 1f
        const val DEFAULT_END = 0f
        const val DEFAULT_CURRENT = 0f
        const val DEFAULT_DURATION = 0f
    }
}

val Entity.fade: Fade
    get() = this[Fade.MAPPER] ?: throw GdxRuntimeException("Fade for entity '$this' is null")