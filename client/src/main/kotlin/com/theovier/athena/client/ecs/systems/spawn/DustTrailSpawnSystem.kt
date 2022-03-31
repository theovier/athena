package com.theovier.athena.client.ecs.systems.spawn

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.extensions.InputDrivenIteratingSystem
import com.theovier.athena.client.ecs.prefabs.Prefab
import ktx.ashley.allOf

class DustTrailSpawnSystem : InputDrivenIteratingSystem(allOf(DustTrail::class, Player::class, Transform::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (input.isStandingStill) {
            return
        }

        val playerPosition = entity.transform.position
        val trail = entity.dustTrail

        if (trail.canSpawn) {
            val origin = Vector2(playerPosition.x, playerPosition.y)
            spawnDustParticle(origin)
            trail.resetTimer()
        } else {
            trail.timer -= deltaTime
        }
    }

    private fun spawnDustParticle(origin: Vector2) {
        Prefab.instantiate("dust_trail") {
            with(transform) {
                position.set(origin, 0f)
            }
        }
    }
}