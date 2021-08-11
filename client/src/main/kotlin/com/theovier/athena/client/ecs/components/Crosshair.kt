package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import kotlinx.serialization.Serializable
import ktx.ashley.mapperFor

@Serializable
class Crosshair : Component, Poolable {
    var radius = 4f

    override fun reset() {
        radius = 0f
    }

    companion object {
        val MAPPER = mapperFor<Crosshair>()
    }
}