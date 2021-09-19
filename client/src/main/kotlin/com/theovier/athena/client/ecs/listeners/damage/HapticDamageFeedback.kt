package com.theovier.athena.client.ecs.listeners.damage

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.controllers.Controllers
import com.theovier.athena.client.weapons.Damage
import com.theovier.athena.client.weapons.DamageSource

class HapticDamageFeedback : DamageListener {
    override fun onDamageTaken(receiver: Entity, damage: Damage) {
        Controllers.getCurrent()?.startVibration(250, 0.25f)
    }

    override fun onDeath(receiver: Entity, source: DamageSource?) = Unit
}