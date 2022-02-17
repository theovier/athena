package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.mapperFor

class Loot : Component, Poolable {
    var lootedBy: Entity? = null
    val isLooted = lootedBy != null

    override fun reset() {
        lootedBy = null
    }

    companion object {
        val MAPPER = mapperFor<Loot>()
    }
}

val Entity.loot: Loot
    get() = this[Loot.MAPPER] ?: throw GdxRuntimeException("Loot for entity '$this' is null")