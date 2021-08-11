package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import kotlinx.serialization.Serializable
import ktx.ashley.mapperFor

@Serializable
class Crosshair : Component, Poolable {
    var minRadius = 2f
    var maxRadius = 10f

    override fun reset() {
        minRadius = 2f
        maxRadius = 10f
    }

    companion object {
        val MAPPER = mapperFor<Crosshair>()
    }
}