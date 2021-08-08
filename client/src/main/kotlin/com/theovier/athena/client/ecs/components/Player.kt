package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import kotlinx.serialization.Serializable
import ktx.ashley.mapperFor

//used to identify player entities
@Serializable
class Player : Component, Poolable {
    companion object {
        val MAPPER = mapperFor<Player>()
    }

    override fun reset() {
        //do nothing
    }
}