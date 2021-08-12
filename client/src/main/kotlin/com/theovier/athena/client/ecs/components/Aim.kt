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
    val maxDistanceToPlayer: Float = 3.0f

    @XmlElement(true)
    @Serializable(with = Vector2Serializer::class)
    //the direction the player aims at (default is facing right)
    var direction = Vector2(1f, 0f)

    @XmlElement(true)
    @Serializable(with = Vector2Serializer::class)
    //where the (invisible) crosshair will move to
    var targetPosition = Vector2()

    override fun reset() {
        targetPosition = Vector2()
        direction = Vector2(1f, 0f)
    }

    companion object {
        val MAPPER = mapperFor<Aim>()
    }
}

val Entity.aim: Aim
    get() = this[Aim.MAPPER] ?: throw GdxRuntimeException("Aim component for entity '$this' is null")