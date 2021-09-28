package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.esotericsoftware.spine.attachments.PointAttachment
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.input
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.weapons.DamageSource
import ktx.ashley.*
import ktx.math.plus
import ktx.math.times
import ktx.math.unaryMinus
import kotlin.with

class PlayerAttackSystem : IteratingSystem(allOf(Player::class, Spine::class).get()) {
    private lateinit var input: Input

    private var timeBetweenShots = 0.2f
    private val knockBackForce = 1f
    private val maxSpray = Vector2(0.125f, 0.125f)
    private var canNextFireInSeconds = 0f

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        input = engine.input
    }

    override fun processEntity(player: Entity, deltaTime: Float) {
        if (input.fire && canFire()) {
            fire(player)
        }
        if (canNextFireInSeconds > 0) {
            canNextFireInSeconds -= deltaTime
        }
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
        val sprayX = MathUtils.random(-maxSpray.x, maxSpray.x)
        val sprayY = MathUtils.random(-maxSpray.y, maxSpray.y)
        val spray = Vector2(sprayX, sprayY)
        val baseDirection = Vector2(1f,0f)
        val shootingDirection = baseDirection.rotateDeg(bulletRotation) + spray
        playFireAnimation(shooter.spine)
        spawnBullet(bulletSpawnOrigin, shootingDirection.nor(), shooter)


        val shellSpawnSlot = skeleton.findSlot("shell_spawn")
        val shellSpawnPoint = shellSpawnSlot.attachment as PointAttachment
        val shellSpawn = shellSpawnPoint.computeWorldPosition(weaponBone, Vector2(shellSpawnPoint.x, shellSpawnPoint.y))
        spawnBulletShell(shellSpawn, shootingDirection.nor())

        applyTrauma(shooter)
        applyKnockBack(shooter)
        canNextFireInSeconds = timeBetweenShots
    }

    private fun playFireAnimation(spine: Spine) {
        spine.state.setAnimation(1, "fire", false)
    }

    private fun spawnBullet(origin: Vector2, direction: Vector2, shooter: Entity) {
        val bullet = Prefab.instantiate(BULLET_ENTITY) {
            with(transform) {
                forward.set(direction)
            }
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

    private fun spawnBulletShell(origin: Vector2, direction: Vector2) {
        val shell = Prefab.instantiate("bullet_shell") {
            with(transform) {
                position.set(origin, 0f)
            }
        }
        val demo = Demo().apply {
            this.origin.set(origin)
            if (direction.x <= 0) {
                velocity.x *= -1
            }
        }

        shell.add(demo)
        shell.add(Spin())
        engine.addEntity(shell)
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