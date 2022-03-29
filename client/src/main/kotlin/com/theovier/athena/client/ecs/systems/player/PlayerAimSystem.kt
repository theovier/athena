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
import com.theovier.athena.client.math.xy
import ktx.ashley.allOf
import ktx.math.plus
import ktx.math.times

class PlayerAimSystem : IteratingSystem(allOf(Aim::class, Player::class, Transform::class).get())  {
    private lateinit var currentController: Controller

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        currentController = Controllers.getCurrent()
    }

    override fun processEntity(player: Entity, deltaTime: Float) {
        val transform = player.transform
        val stickInput = pollProcessedInput()
        val direction = stickInput.nor()
        val isAiming = direction.isNotZero
        player.aim.isCurrentlyAiming = isAiming

        if (isAiming) {
            player.aim.direction = direction
            transform.forward.set(direction)
        }
    }

    private fun pollInput(): Vector2 {
        val xAxisValueRaw = currentController.getAxis(XboxInputAdapter.AXIS_RIGHT_X)
        val yAxisValueRaw = -currentController.getAxis(XboxInputAdapter.AXIS_RIGHT_Y)
        return Vector2(xAxisValueRaw, yAxisValueRaw)
    }

    private fun pollProcessedInput(): Vector2 {
        var stickInput = pollInput()
        if (isAxisInputInDeadZone(stickInput)) {
            stickInput = Vector2.Zero
        }
        return stickInput
    }
}