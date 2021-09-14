package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.theovier.athena.client.weapons.Damage
import ktx.ashley.get
import ktx.ashley.mapperFor

class Damage(val damage: Damage) : Component {
    companion object {
        val MAPPER = mapperFor<com.theovier.athena.client.ecs.components.Damage>()
    }
}

val Entity.damageComponent: com.theovier.athena.client.ecs.components.Damage
    get() = this[com.theovier.athena.client.ecs.components.Damage.MAPPER] ?: throw GdxRuntimeException("Damage for entity '$this' is null")