package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.math.xy
import ktx.app.KtxInputAdapter
import ktx.ashley.allOf

class PlayerAttackSystem : KtxInputAdapter, IteratingSystem(allOf(Player::class, Aim::class).get()) {
    private var wantsToFire = false

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        (Gdx.input.inputProcessor as InputMultiplexer).addProcessor(this)
    }

    override fun removedFromEngine(engine: Engine?) {
        super.removedFromEngine(engine)
        (Gdx.input.inputProcessor as InputMultiplexer).removeProcessor(this)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (wantsToFire) {
            val playerPosition = entity.transform.position.xy
            spawnBullet(playerPosition, entity.aim.direction)
            wantsToFire = false
        }
    }

    private fun spawnBullet(origin: Vector2, direction: Vector2) {
        Prefab.instantiate("bullet") {
            with(transform) {
                rotation = direction.angleDeg()
                position.set(origin.x, origin.y, 0f)
            }
            with(movement) {
                this.direction = direction
            }
            engine.addEntity(this)
        }
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        when (button) {
            Input.Buttons.LEFT -> wantsToFire = true
            else -> return false
        }
        return true
    }
}