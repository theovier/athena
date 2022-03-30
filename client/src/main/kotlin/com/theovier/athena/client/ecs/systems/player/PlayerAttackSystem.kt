package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.esotericsoftware.spine.attachments.PointAttachment
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.movement.direction
import com.theovier.athena.client.ecs.components.movement.velocity
import com.theovier.athena.client.ecs.extensions.InputDrivenIteratingSystem
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.misc.physics.CollisionCategory
import com.theovier.athena.client.weapons.DamageSource
import ktx.ashley.*
import ktx.math.times
import ktx.math.unaryMinus
import kotlin.with

class PlayerAttackSystem : InputDrivenIteratingSystem(allOf(Player::class, Spine::class).get()) {
    private var timeBetweenShots = 0.2f
    private val knockBackForce = 1f
    private val maxSpray = Vector2(0.125f, 0.125f)
    private var canNextFireInSeconds = 0f

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
        //val shootingDirection = baseDirection.rotateDeg(bulletRotation) + spray
        val shootingDirection = baseDirection.rotateDeg(bulletRotation) //todo: add spray back in when finished with aim debugging
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

    private fun spawnBullet(origin: Vector2, shootingDirection: Vector2, shooter: Entity) {
        Prefab.instantiate(BULLET_ENTITY) {
            with(transform) {
                forward.set(shootingDirection)
            }
            with(direction) {
                direction = shootingDirection
            }
            with(physics) {
                body.isBullet = true
                body.setTransform(Vector2(origin.x, origin.y), shootingDirection.angleRad())
                body.fixtureList.first().filterData.categoryBits = CollisionCategory.BULLET
            }
            with(damageComponent) {
                damage.source = DamageSource(this@instantiate, shooter)
            }
        }
    }

    private fun spawnBulletShell(origin: Vector2, direction: Vector2) {
        Prefab.instantiate("bullet_shell") {
            with(transform) {
                position.set(origin, 0f)
            }
            with(travel) {
                this.origin.set(origin)
            }
            with(velocity) {
                velocity.x = MathUtils.random(-5f, -10f)
                velocity.y = MathUtils.random(5f, 10f)
                if (direction.x <= 0) {
                    velocity.x *= -1
                }
            }
        }
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