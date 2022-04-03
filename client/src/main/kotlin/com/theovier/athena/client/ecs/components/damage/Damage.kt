package com.theovier.athena.client.ecs.components.damage

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.theovier.athena.client.weapons.Damage as Dmg
import ktx.ashley.get
import ktx.ashley.mapperFor

class Damage(val damage: Dmg) : Component {
    companion object {
        val MAPPER = mapperFor<Damage>()
    }
}

val Entity.damageComponent: Damage
    get() = this[Damage.MAPPER] ?: throw GdxRuntimeException("Damage for entity '$this' is null")