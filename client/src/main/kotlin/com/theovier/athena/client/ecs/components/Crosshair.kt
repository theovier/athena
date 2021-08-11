package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import kotlinx.serialization.Serializable
import ktx.ashley.mapperFor

@Serializable
class Crosshair : Component, Poolable {
    var minRadius = 4f
    var maxRadius = 8f

    override fun reset() {
        minRadius = 4f
        maxRadius = 8f
    }

    companion object {
        val MAPPER = mapperFor<Crosshair>()
    }
}