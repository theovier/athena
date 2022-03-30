package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.esotericsoftware.spine.attachments.PointAttachment
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.extensions.InputDrivenIteratingSystem
import com.theovier.athena.client.misc.physics.CollisionCategory
import ktx.ashley.allOf
import ktx.box2d.RayCast
import ktx.box2d.rayCast
import ktx.math.minus
import ktx.math.plus
import ktx.math.times
import mu.KotlinLogging

class AimAssistSystem(private val world: World) : InputDrivenIteratingSystem(
    allOf(Player::class, AimAssist::class, Aim::class, Transform::class, Spine::class).get()) {
    private val log = KotlinLogging.logger {}

    override fun processEntity(player: Entity, deltaTime: Float) {
        val aim = player.aim

        if (!input.isAiming) {
            return
        }

        val assist = player.aimAssist
        val spine = player.spine
        val aimingDirection = aim.direction
        val start = spine.getMuzzlePosition()

        //todo refactor

        val targets = ArrayList<Entity>()

        //todo rewrite the for loop, so the first found entity is the closest to the actual angle
        for (angle in -assist.maxAngle ..assist.maxAngle step assist.degreesBetweenAngleChecks) {
            val direction = aimingDirection.cpy()
            direction.rotateDeg(angle.toFloat())
            val end = start + direction * assist.distance
            val target = raycast(start, end)
            if (target != null && !targets.contains(target)) {
                targets.add(target)
            }
        }

        if (targets.isEmpty()) {
            //do not modify aim, no enemies in sight
            return
        }

        val bestTarget = targets.first()
        val bestTargetPosition = bestTarget.transform.center


        val correctedDirection = (bestTargetPosition - start).nor() //todo problem of snapping here?


        aim.direction = correctedDirection


        //todo select closest

    }

    //todo clean up
    private fun raycast(start: Vector2, end: Vector2): Entity? {
        var target: Entity? = null
        world.rayCast(start, end) { fixture, _, _, _ ->

            if (fixture.filterData.categoryBits == CollisionCategory.ENEMY) {
                val userData = fixture.body.userData
                if (userData is Entity) {
                    val entity = userData
                    if (entity.isTargetable) {
                        target = userData
                    }
                    RayCast.TERMINATE
                }
            }
            RayCast.CONTINUE
        }
        return target
    }
}