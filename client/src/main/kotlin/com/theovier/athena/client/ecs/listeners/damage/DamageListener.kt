package com.theovier.athena.client.ecs.listeners.damage

import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.weapons.Damage
import com.theovier.athena.client.weapons.DamageSource

interface DamageListener {
    fun onDamageTaken(receiver: Entity, damage: Damage)
    fun onDeath(receiver: Entity, source: DamageSource?)
}