package com.theovier.athena.client.ecs.listeners.damage

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.hasSpineComponent
import com.theovier.athena.client.ecs.components.spine
import com.theovier.athena.client.ecs.components.text
import com.theovier.athena.client.ecs.components.transform
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.weapons.Damage
import com.theovier.athena.client.weapons.DamageSource

class DamageIndicatorSpawner(private val engine: Engine) : DamageListener {

    override fun onDamageTaken(receiver: Entity, damage: Damage) {
        if (receiver.hasSpineComponent && receiver.spine.hasDamageIndicator) {
            val spine = receiver.spine
            val position = spine.getRandomDamageIndicatorPosition()
            spawnDamageIndicator(position, damage)
        }
    }

    private fun spawnDamageIndicator(atPosition: Vector2, damage: Damage) {
        val indicator = Prefab.instantiate("damageIndicator") {
            with(transform) {
                position.set(atPosition, 0f)
            }
            with(text) {
                text = "${damage.amount}"
            }
        }
        engine.addEntity(indicator)
    }

    override fun onDeath(receiver: Entity, source: DamageSource?) = Unit
}