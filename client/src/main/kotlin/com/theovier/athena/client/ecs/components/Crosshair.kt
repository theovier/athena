package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import com.theovier.athena.client.ecs.prefabs.serializers.Vector2Serializer
import kotlinx.serialization.Serializable
import ktx.ashley.get
import ktx.ashley.mapperFor
import nl.adaptivity.xmlutil.serialization.XmlElement

@Serializable
class Crosshair : Component, Poolable {
    var minRadius = 4f
    var maxRadius = 8f

    @XmlElement(true)
    @Serializable(with = Vector2Serializer::class)
    var targetPosition = Vector2()

    override fun reset() {
        minRadius = 4f
        maxRadius = 8f
    }

    companion object {
        val MAPPER = mapperFor<Crosshair>()
    }
}

val Entity.crosshair: Crosshair
    get() = this[Crosshair.MAPPER] ?: throw GdxRuntimeException("Crosshair for entity '$this' is null")