package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import ktx.ashley.has
import ktx.ashley.mapperFor

//used to identify targetable enemies/objects
class Targetable : Component {
    companion object {
        val MAPPER = mapperFor<Targetable>()
    }
}

val Entity.isTargetable: Boolean
    get() = this.has(Targetable.MAPPER)