package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.mapperFor

class Aim : Component, Poolable {
    //the direction the player aims at
    var direction = Vector2(0f, 0f)

    var isCurrentlyAiming = false
    val isNotCurrentlyAiming: Boolean
        get() = !isCurrentlyAiming

    override fun reset() {
        direction = Vector2(1f, 0f)
    }

    companion object {
        val MAPPER = mapperFor<Aim>()
    }
}

val Entity.aim: Aim
    get() = this[Aim.MAPPER] ?: throw GdxRuntimeException("Aim component for entity '$this' is null")