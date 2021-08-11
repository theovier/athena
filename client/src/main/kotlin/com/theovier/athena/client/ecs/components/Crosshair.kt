package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import kotlinx.serialization.Serializable
import ktx.ashley.mapperFor

@Serializable
class Crosshair : Component, Poolable {
    var minRadius = 4f
    var maxRadius = 15f

    override fun reset() {
        minRadius = 4f
        maxRadius = 10f
    }

    companion object {
        val MAPPER = mapperFor<Crosshair>()
    }
}