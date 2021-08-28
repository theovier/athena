package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.inputs.XboxInputAdapter
import ktx.ashley.allOf

class PlayerMovementSystem : IteratingSystem(allOf(Player::class, Movement::class, SkeletalAnimation::class).get()) {
    private lateinit var currentController: Controller

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        currentController = Controllers.getCurrent()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val xAxisValueRaw = currentController.getAxis(XboxInputAdapter.AXIS_LEFT_X)
        val yAxisValueRaw = -currentController.getAxis(XboxInputAdapter.AXIS_LEFT_Y)
        var stickInput = Vector2(xAxisValueRaw, yAxisValueRaw)
        if (stickInput.len() < XboxInputAdapter.MOVE_DEAD_ZONE) {
            stickInput = Vector2.Zero
        }
        val playerMovement = entity.movement
        val skeletalAnimation = entity.skeletalAnimation
        val direction = stickInput
        playerMovement.direction = direction
        playAnimation(skeletalAnimation, direction)
    }

    private fun playAnimation(skeletalAnimation: SkeletalAnimation, direction: Vector2) {
        if (direction.isZero) {
            skeletalAnimation.playAnimationIfNotAlreadyPlaying(name = "idle")
        } else {
            skeletalAnimation.playAnimationIfNotAlreadyPlaying(name = "run")
        }
    }
}