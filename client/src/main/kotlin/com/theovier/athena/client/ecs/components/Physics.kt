package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.mapperFor

class Physics : Component, Poolable {
    lateinit var body: Body

    override fun reset() {
        body.world.destroyBody(body)
        body.userData = null
    }

    companion object {
        val MAPPER = mapperFor<Physics>()
    }
}

val Entity.physics: Physics
    get() = this[Physics.MAPPER] ?: throw GdxRuntimeException("Body for entity '$this' is null")