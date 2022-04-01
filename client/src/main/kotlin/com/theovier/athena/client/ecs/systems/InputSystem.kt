package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.Input
import com.theovier.athena.client.ecs.extensions.InputDrivenIteratingSystem
import com.theovier.athena.client.inputs.XboxInputAdapter
import ktx.ashley.allOf

class InputSystem : InputDrivenIteratingSystem(allOf(Input::class).get()), XboxInputAdapter  {
    private lateinit var controller: Controller

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        controller = Controllers.getCurrent()
        controller.addListener(this)
    }

    override fun removedFromEngine(engine: Engine) {
        super.removedFromEngine(engine)
        controller.removeListener(this)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val moveAxisHorizontal = controller.getAxis(XboxInputAdapter.AXIS_LEFT_X)
        val moveAxisVertical = -controller.getAxis(XboxInputAdapter.AXIS_LEFT_Y)
        val aimAxisHorizontal = controller.getAxis(XboxInputAdapter.AXIS_RIGHT_X)
        val aimAxisVertical = -controller.getAxis(XboxInputAdapter.AXIS_RIGHT_Y)
        val fireAxisValue = controller.getAxis(XboxInputAdapter.AXIS_TRIGGER_RIGHT)
        input.fire = fireAxisValue > XboxInputAdapter.FIRE_DEAD_ZONE
        input.rawMovement.set(moveAxisHorizontal, moveAxisVertical)
        input.rawAim.set(aimAxisHorizontal, aimAxisVertical)
        input.aim.set(zeroOutWhenInDeadZone(input.rawAim))
        input.movement.set(zeroOutWhenInDeadZone(input.rawMovement))
        input.isMoving = !(XboxInputAdapter.isAxisInputInDeadZone(input.movement))
        input.isAiming = !(XboxInputAdapter.isAxisInputInDeadZone(input.aim))
    }

    override fun buttonDown(controller: Controller, buttonCode: Int): Boolean {
        when (buttonCode) {
            XboxInputAdapter.BUTTON_Y -> input.hideHealthBars = !input.hideHealthBars
            XboxInputAdapter.BUTTON_A -> input.dash = true
            else -> {
                return false
            }
        }
        return true
    }

    override fun buttonUp(controller: Controller, buttonCode: Int): Boolean {
        when (buttonCode) {
            XboxInputAdapter.BUTTON_A -> input.dash = false
            else -> {
                return false
            }
        }
        return true
    }

    private fun zeroOutWhenInDeadZone(input: Vector2): Vector2 {
        if (XboxInputAdapter.isAxisInputInDeadZone(input)) {
            return Vector2.Zero
        }
        return input
    }
}