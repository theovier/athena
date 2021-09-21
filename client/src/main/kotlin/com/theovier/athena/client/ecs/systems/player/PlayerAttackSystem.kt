package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.inputs.XboxInputAdapter
import com.theovier.athena.client.weapons.DamageSource
import ktx.ashley.allOf
import ktx.assets.async.AssetStorage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlayerAttackSystem : XboxInputAdapter, IteratingSystem(allOf(Player::class, Aim::class, Spine::class).get()), KoinComponent {
    private lateinit var currentController: Controller
    private var wantsToFire = false

    //testwise
    private val assets: AssetStorage by inject()

    //todo use weapons and dedicated components instead
    private var timeBetweenShots = 0.2f
    private var canNextFireInSeconds = 0f

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
        if (canNextFireInSeconds > 0) {
            canNextFireInSeconds -= deltaTime
        }
    }

    private fun processInput() {
        val axisValueRaw = currentController.getAxis(XboxInputAdapter.AXIS_TRIGGER_RIGHT)
        wantsToFire = axisValueRaw >= XboxInputAdapter.FIRE_DEAD_ZONE
    }

    private fun canFire(): Boolean {
        return canNextFireInSeconds <= 0
    }

    private fun fire(shooter: Entity) {
        val headBone = shooter.spine.skeleton.findBone("head")
        val origin = Vector2(headBone.worldX, headBone.worldY)
        spawnBullet(origin, shooter.aim.direction, shooter)
        canNextFireInSeconds = timeBetweenShots
        wantsToFire = false
    }

    private fun spawnBullet(origin: Vector2, direction: Vector2, shooter: Entity) {
        val bullet = Prefab.instantiate(BULLET_ENTITY) {
            with(movement) {
                this.direction = direction
            }
            with(physics) {
                body.isBullet = true
                body.setTransform(Vector2(origin.x, origin.y + 2f), direction.angleRad())
            }
            with(damageComponent) {
                damage.source = DamageSource(this@instantiate, shooter)
            }
        }

        val soundComponent = Sound().apply {
            sound = assets.loadSync("audio/gun_fire-0${MathUtils.random(1, 3)}.ogg")
            volume = 0.1f
        }
        bullet.add(soundComponent)

        engine.addEntity(bullet)
    }

    companion object {
        const val BULLET_ENTITY = "bullet"
    }
}