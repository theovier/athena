package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.math.compareTo

class Transform : Component, Poolable, Comparable<Transform> {
    val position = Vector3()
    val size = Vector2(1f, 1f)
    var rotation = 0f

    override fun reset() {
        position.set(0f, 0f, 0f)
        size.set(1f, 1f)
        rotation = 0f
    }

    override fun compareTo(other: Transform): Int {
        return position.compareTo(other.position)
    }

    companion object {
        val MAPPER = mapperFor<Transform>()
    }
}

val Entity.transform: Transform
    get() = this[Transform.MAPPER] ?: throw GdxRuntimeException("Transform for entity '$this' is null")
