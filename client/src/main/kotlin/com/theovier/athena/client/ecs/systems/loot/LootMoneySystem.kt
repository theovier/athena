package com.theovier.athena.client.ecs.systems.loot

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf

class LootMoneySystem : IteratingSystem(allOf(Loot::class, Looted::class, Money::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val looter = entity.looted.lootedBy

        if (!looter.hasMoneyComponent) {
            looter.add(Money())
        }

        looter.money.amount += entity.money.amount
    }
}