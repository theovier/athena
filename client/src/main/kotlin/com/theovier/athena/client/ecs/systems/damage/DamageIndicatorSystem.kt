package com.theovier.athena.client.ecs.systems.damage

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.weapons.Damage
import com.theovier.athena.client.weapons.DamageType
import ktx.ashley.allOf

class DamageIndicatorSystem : IteratingSystem(allOf(HitMarker::class, Spine::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val spine = entity.spine
        if (spine.hasNoDamageIndicator) {
            return
        }
        entity.hitmarker.hits.forEach { hit ->
            val position = spine.getRandomDamageIndicatorPosition()
            spawnDamageIndicator(position, hit)
        }
    }

    private fun spawnDamageIndicator(atPosition: Vector2, hit: Damage) {
       Prefab.instantiate("damageIndicator") {
            with(transform) {
                position.set(atPosition, 0f)
            }
            with(text) {
                text = "${hit.amount}"
                color = Color.valueOf(damageTypeToColor[hit.type])
            }
        }
    }

    companion object {
        val damageTypeToColor = mapOf(
            DamageType.PHYSICAL to "#ffffff",
            DamageType.ELECTRIC to "#ffff00",
            DamageType.FIRE to "#ff8815",
            DamageType.POISON to "#ffffff",
        )
    }
}