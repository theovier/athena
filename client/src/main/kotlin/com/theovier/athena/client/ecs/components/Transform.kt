package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import com.theovier.athena.client.ecs.prefabs.serializers.Vector2Serializer
import com.theovier.athena.client.ecs.prefabs.serializers.Vector3Serializer
import kotlinx.serialization.Serializable
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.math.compareTo
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
class Transform : Component, Poolable, Comparable<Transform> {

    @XmlElement(true)
    @XmlSerialName("Position", "", "")
    @Serializable(with = Vector3Serializer::class)
    val position = Vector3()

    @XmlElement(true)
    @XmlSerialName("Size", "", "")
    @Serializable(with = Vector2Serializer::class)
    val size = Vector2(1f, 1f)

    /* radians */
    var rotation = 0f

    val rotationDegrees: Float
        get() = rotation * MathUtils.radiansToDegrees

    override fun reset() {
        position.set(0f, 0f, 0f)
        size.set(1f, 1f)
        rotation = 0f
    }


    /* compares foremost on the y-axis to determine which transform is overlapping the other */
    override fun compareTo(other: Transform): Int {
        val yDifference = other.position.y.compareTo(position.y)
        if (yDifference == 0) {
            return position.compareTo(other.position)
        }
        return yDifference
    }

    companion object {
        val MAPPER = mapperFor<Transform>()
    }
}

val Entity.transform: Transform
    get() = this[Transform.MAPPER] ?: throw GdxRuntimeException("Transform for entity '$this' is null")
