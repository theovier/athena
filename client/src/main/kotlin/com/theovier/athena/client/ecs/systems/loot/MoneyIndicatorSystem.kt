package com.theovier.athena.client.ecs.systems.loot

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.movement.Velocity
import com.theovier.athena.client.ecs.prefabs.Prefab
import ktx.ashley.allOf

class MoneyIndicatorSystem : IteratingSystem(allOf(Looted::class, Money::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val looter = entity.looted.lootedBy
        val position = looter.transform.position

        Prefab.instantiate("moneyGainIndicator").apply {
            with(transform) {
                this.position.set(position.x, this.position.y + position.y, position.z)
            }
            with(text) {
                text = "+ $ ${entity.money.amount}"
                color = Color.GREEN //todo add color to text component loader
            }
            //todo add fade system for text
        }
    }
}