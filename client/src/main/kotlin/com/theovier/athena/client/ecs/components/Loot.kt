package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.mapperFor

class Loot : Component, Poolable {
    override fun reset() = Unit

    companion object {
        val MAPPER = mapperFor<Loot>()
    }
}

val Entity.loot: Loot
    get() = this[Loot.MAPPER] ?: throw GdxRuntimeException("Loot for entity '$this' is null")