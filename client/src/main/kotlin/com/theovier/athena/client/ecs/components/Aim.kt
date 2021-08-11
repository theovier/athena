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
class Aim : Component, Poolable {

    @XmlElement(true)
    @Serializable(with = Vector2Serializer::class)
    var direction = Vector2()

    var distanceToAimTarget = 0f

    override fun reset() {
        direction = Vector2()
        distanceToAimTarget = 0f
    }

    companion object {
        val MAPPER = mapperFor<Aim>()
    }
}

val Entity.aim: Aim
    get() = this[Aim.MAPPER] ?: throw GdxRuntimeException("Aim component for entity '$this' is null")