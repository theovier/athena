package com.theovier.athena.client.ecs.components.animation

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

class Wiggle : Component, Poolable {
    override fun reset() = Unit
    companion object {
        val MAPPER = mapperFor<Wiggle>()
    }
}