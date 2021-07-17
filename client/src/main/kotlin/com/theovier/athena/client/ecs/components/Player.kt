package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

//used to identify player entities
class Player : Component, Poolable {
    companion object {
        val mapper = mapperFor<Player>()
    }

    override fun reset() {
        //do nothing
    }
}