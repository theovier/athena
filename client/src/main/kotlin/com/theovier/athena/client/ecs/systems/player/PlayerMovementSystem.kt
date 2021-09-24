package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.inputs.XboxInputAdapter
import com.theovier.athena.client.inputs.XboxInputAdapter.Companion.isAxisInputInDeadZone
import com.theovier.athena.client.math.isNotZero
import com.theovier.athena.client.misc.spine.playAnimationIfNotAlreadyPlaying
import ktx.ashley.allOf

class PlayerMovementSystem : IteratingSystem(allOf(Player::class, Transform::class, Movement::class, Spine::class).get()) {
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
        val transform = player.transform
        val movement = player.movement
        val spine = player.spine
        val direction = stickInput

        movement.direction = direction
        if (direction.isNotZero) {
            transform.forward.set(direction)
        }
        playAnimation(spine, direction)
    }

    private fun pollInput(): Vector2 {
        val xAxisValueRaw = currentController.getAxis(XboxInputAdapter.AXIS_LEFT_X)
        val yAxisValueRaw = -currentController.getAxis(XboxInputAdapter.AXIS_LEFT_Y)
        return Vector2(xAxisValueRaw, yAxisValueRaw)
    }

    private fun playAnimation(spine: Spine, direction: Vector2) {
        if (direction.isZero) {
            spine.state.playAnimationIfNotAlreadyPlaying(name = "idle")
        } else {
            spine.state.playAnimationIfNotAlreadyPlaying(name = "run")
        }
    }
}