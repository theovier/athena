package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.Vector2
import com.esotericsoftware.spine.attachments.PointAttachment
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.inputs.XboxInputAdapter
import com.theovier.athena.client.misc.spine.playAnimationIfNotAlreadyPlaying
import com.theovier.athena.client.weapons.DamageSource
import ktx.ashley.*
import ktx.math.times
import ktx.math.unaryMinus
import kotlin.with

class PlayerAttackSystem : XboxInputAdapter, IteratingSystem(allOf(Player::class, Spine::class).get()) {
    private lateinit var currentController: Controller
    private var wantsToFire = false
    private var timeBetweenShots = 0.2f
    private val knockBackForce = 1f
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
        val skeleton = shooter.spine.skeleton
        val weaponBone = skeleton.findBone("weapon")
        val bulletSpawnPointSlot = skeleton.findSlot("bullet_spawn")
        val bulletSpawnPointAttachment = bulletSpawnPointSlot.attachment as PointAttachment
        val bulletRotation = bulletSpawnPointAttachment.computeWorldRotation(weaponBone)
        val bulletSpawnOrigin = bulletSpawnPointAttachment.computeWorldPosition(
            weaponBone,
            Vector2(bulletSpawnPointAttachment.x, bulletSpawnPointAttachment.y)
        )
        val shootingDirection = Vector2(1f,0f).rotateDeg(bulletRotation)
        spawnMuzzleFlash(shooter.spine)
        spawnBullet(bulletSpawnOrigin, shootingDirection, shooter)
        applyTrauma(shooter)
        applyKnockBack(shooter)
        canNextFireInSeconds = timeBetweenShots
        wantsToFire = false
    }

    private fun spawnMuzzleFlash(spine: Spine) {
        spine.state.playAnimationIfNotAlreadyPlaying(0, "fire")
    }

    private fun spawnBullet(origin: Vector2, direction: Vector2, shooter: Entity) {
        val bullet = Prefab.instantiate(BULLET_ENTITY) {
            with(movement) {
                this.direction = direction
            }
            with(physics) {
                body.isBullet = true
                body.setTransform(Vector2(origin.x, origin.y), direction.angleRad())
            }
            with(damageComponent) {
                damage.source = DamageSource(this@instantiate, shooter)
            }
        }
        engine.addEntity(bullet)
    }

    private fun applyTrauma(shooter: Entity) {
        if (shooter.hasTraumaComponent) {
            shooter.trauma.trauma += 0.5f
        }
    }

    private fun applyKnockBack(shooter: Entity) {
        if (shooter.hasPhysicsComponent) {
            val position = shooter.transform.position
            val knockBackDirection = -shooter.transform.forward
            val knockBack = knockBackDirection * knockBackForce
            shooter.physics.body.applyLinearImpulse(knockBack.x, knockBack.y, position.x, position.y, true)
        }
    }

    companion object {
        const val BULLET_ENTITY = "bullet"
    }
}