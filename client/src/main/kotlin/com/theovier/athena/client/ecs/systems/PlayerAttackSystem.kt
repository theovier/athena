package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.Timer
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.inputs.XboxInputAdapter
import com.theovier.athena.client.math.xy
import ktx.ashley.allOf

class PlayerAttackSystem : XboxInputAdapter, IteratingSystem(allOf(Player::class, Aim::class).get()) {
    private lateinit var currentController: Controller
    private var wantsToFire = false

    //todo use weapons and dedicated components instead
    private var timeBetweenShots = 0.2f
    private var lastShotFiredAt = 0L

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        currentController = Controllers.getCurrent()
        currentController.addListener(this)
    }

    override fun removedFromEngine(engine: Engine?) {
        super.removedFromEngine(engine)
        currentController.removeListener(this)
    }

    override fun processEntity(player: Entity, deltaTime: Float) {
        processInput()
        if (wantsToFire && canFire()) {
            fire(player)
        }
    }

    private fun processInput() {
        val axisValueRaw = currentController.getAxis(XboxInputAdapter.AXIS_TRIGGER_RIGHT)
        if (axisValueRaw >= XboxInputAdapter.FIRE_DEAD_ZONE) {
            wantsToFire = true
        }
    }

    private fun canFire(): Boolean {
        return TimeUtils.timeSinceMillis(lastShotFiredAt) >= timeBetweenShots * MILLIS_PER_SECOND
    }

    private fun fire(shooter: Entity) {
        val origin = shooter.transform.position.xy
        spawnBullet(origin, shooter.aim.direction)
        lastShotFiredAt = TimeUtils.millis()
        wantsToFire = false
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

    companion object {
        const val MILLIS_PER_SECOND = 1000
    }
}