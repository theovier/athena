package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.mapperFor

class Looted : Component {
    lateinit var lootedBy: Entity

    companion object {
        val MAPPER = mapperFor<Looted>()
    }
}

val Entity.looted: Looted
    get() = this[Looted.MAPPER] ?: throw GdxRuntimeException("Looted for entity '$this' is null")