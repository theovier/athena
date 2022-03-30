package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
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

private val log = KotlinLogging.logger {}

class AimAssistSystem(private val world: World) : InputDrivenIteratingSystem(
    allOf(Player::class, AimAssist::class, Aim::class, Transform::class, Spine::class).get()) {

    override fun processEntity(player: Entity, deltaTime: Float) {
        if (input.isNotAiming) {
            return
        }
        val targets = ArrayList<Entity>()
        val transform = player.transform
        val aim = player.aim
        val assist = player.aimAssist
        val spine = player.spine
        val aimingDirection = aim.direction
        val aimOrigin = spine.getMuzzlePosition()

        // first check the closest angles, then increase in both directions simultaneously
        for (angle in 0..assist.maxAngle step assist.degreesBetweenAngleChecks) {
            for (sign in intArrayOf(-1, 1)) {
                val newAngle = angle * sign
                val direction = aimingDirection.cpy()
                direction.rotateDeg(newAngle.toFloat())
                val aimDestination = aimOrigin + direction * assist.distance
                val target = raycast(aimOrigin, aimDestination)
                if (target != null && !targets.contains(target)) {
                    targets.add(target)
                }
            }
        }
        if (targets.isEmpty()) {
            //do not modify aim, no enemies in sight
            return
        }

        //todo: maybe add decision between closer targets and better angle
        val closestAngleTarget = targets.first()
        val target = closestAngleTarget
        val targetPosition = target.transform.center

        if (targetPosition.dst2(transform.center) <= assist.deadzone) {
            //target too close for aim assist
            return
        }

        aim.direction = (targetPosition - aimOrigin).nor()
    }

    private fun getClosestDistanceTarget(origin: Vector2, targets: List<Entity>): Entity {
        return targets.minByOrNull {
            it.transform.center.dst2(origin)
        }!!
    }

    //todo problem with other physic elements, e.g., (bushes) / bullets?
    private fun raycast(start: Vector2, end: Vector2): Entity? {
        var target: Entity? = null
        world.rayCast(start.cpy(), end.cpy()) { fixture, _, _, _ ->

            if (fixture.filterData.categoryBits == CollisionCategory.ENEMY) {
                val userData = fixture.body.userData
                if (userData is Entity) {
                    val entity = userData
                    if (entity.isTargetable) {
                        target = userData
                        RayCast.TERMINATE
                    }
                }
            } else {
                log.debug { fixture.filterData.categoryBits }
            }
            RayCast.CONTINUE
        }
        return target
    }
}