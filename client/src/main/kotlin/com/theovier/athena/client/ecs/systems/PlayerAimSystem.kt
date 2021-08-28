package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.inputs.XboxInputAdapter
import com.theovier.athena.client.inputs.XboxInputAdapter.Companion.isAxisInputInDeadZone
import com.theovier.athena.client.math.xy
import ktx.ashley.allOf
import ktx.math.plus
import ktx.math.times

class PlayerAimSystem : IteratingSystem(allOf(Aim::class, Player::class, Transform::class, SkeletalAnimation::class).get())  {
    private lateinit var currentController: Controller

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        currentController = Controllers.getCurrent()
    }

    override fun processEntity(player: Entity, deltaTime: Float) {
        var stickInput = pollInput()
        if (isAxisInputInDeadZone(stickInput)) {
            stickInput = Vector2.Zero
        }
        val playerPosition = player.transform.position.xy
        val targetPositionRelativeToPlayer = stickInput * player.aim.maxDistanceToPlayer
        player.aim.targetPosition = playerPosition + targetPositionRelativeToPlayer

        val direction = stickInput.nor()
        if (!direction.isZero) {
            player.aim.direction = direction
            faceTowardsAimDirection(player.skeletalAnimation, direction)
        }
    }

    private fun pollInput(): Vector2 {
        val xAxisValueRaw = currentController.getAxis(XboxInputAdapter.AXIS_RIGHT_X)
        val yAxisValueRaw = -currentController.getAxis(XboxInputAdapter.AXIS_RIGHT_Y)
        return Vector2(xAxisValueRaw, yAxisValueRaw)
    }

    private fun faceTowardsAimDirection(animation: SkeletalAnimation, direction: Vector2) {
        if (direction.x < 0) {
            animation.forceToFaceLeft()
        } else {
            animation.forceToFaceRight()
        }
    }
}