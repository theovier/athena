package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

//used to identify player entities
class Player : Component, Poolable {
    override fun reset() = Unit

    companion object {
        val MAPPER = mapperFor<Player>()
    }
}