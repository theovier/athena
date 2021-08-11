package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.inputs.XboxInputAdapter
import com.theovier.athena.client.math.xy
import ktx.ashley.allOf

class PlayerAttackSystem : XboxInputAdapter, IteratingSystem(allOf(Player::class, Aim::class).get()) {
    private lateinit var currentController: Controller
    private var wantsToFire = false

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        currentController = Controllers.getCurrent()
        currentController.addListener(this)
    }

    override fun removedFromEngine(engine: Engine?) {
        super.removedFromEngine(engine)
        currentController.removeListener(this)
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

    override fun axisMoved(controller: Controller?, axis: Int, value: Float): Boolean {
        when(axis) {
            XboxInputAdapter.AXIS_TRIGGER_RIGHT -> wantsToFire = true
            else -> return false
        }
        return true
    }
}