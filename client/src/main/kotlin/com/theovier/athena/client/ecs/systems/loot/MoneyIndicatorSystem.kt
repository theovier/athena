package com.theovier.athena.client.ecs.systems.loot

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.movement.Velocity
import com.theovier.athena.client.ecs.prefabs.Prefab
import ktx.ashley.allOf
import kotlin.math.absoluteValue

class MoneyIndicatorSystem : IteratingSystem(allOf(Looted::class, Money::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val money = entity.money
        val looter = entity.looted.lootedBy
        val position = looter.transform.position

        val gainsMoney = money.amount >= 0
        val gainMoneyText = "+  ${money.amount.absoluteValue}"
        val loseMoneyText = "-  ${money.amount.absoluteValue}"

        Prefab.instantiate("moneyGainIndicator").apply {
            with(transform) {
                this.position.set(position.x, this.position.y + position.y, position.z)
            }
            with(text) {
                text = if (gainsMoney) gainMoneyText else loseMoneyText
                color = if (gainsMoney) Color.GREEN else Color.RED
            }
            //todo add fade system for text
        }
    }
}