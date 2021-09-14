package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Damage
import com.theovier.athena.client.weapons.DamageType

class DamageComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Damage {
        val amount = componentJSON.getInt("amount")
        val damage = com.theovier.athena.client.weapons.Damage(amount, DamageType.PHYSICAL, null)
        return Damage(damage)
    }
}