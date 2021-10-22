package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.input
import com.theovier.athena.client.ecs.prefabs.Prefab
import ktx.ashley.allOf

class DustTrailSpawnSystem : IteratingSystem(allOf(DustTrail::class, Player::class, Transform::class).get()) {
    lateinit var input: Input

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        input = engine.input
    }

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