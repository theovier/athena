package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor

class Transform : Component, Pool.Poolable, Comparable<Transform> {
    val z_default = 0
    val position = Vector3()
    val size = Vector2(1f, 1f)
    val boundingBoxOffset = Vector2(0f, 0f)
    val width: Float
        get() = size.x + boundingBoxOffset.x
    val height: Float
        get() = size.y + boundingBoxOffset.y

    override fun reset() {
        position.set(0f, 0f, z_default.toFloat())
        size.set(1f, 1f)
        boundingBoxOffset.set(0f, 0f)
    }

    override fun compareTo(other: Transform): Int {
        val zDiff = position.z.compareTo(other.position.z)
        return if (zDiff == 0) other.position.y.compareTo(position.y) else zDiff
    }

    companion object {
        val mapper = mapperFor<Transform>()
        val TMP_RECT_1 = Rectangle()
        val TMP_RECT_2 = Rectangle()
    }
}

val Entity.transformComponent: Transform
    get() = this[Transform.mapper] ?: throw GdxRuntimeException("TransformComponent for entity '$this' is null")
