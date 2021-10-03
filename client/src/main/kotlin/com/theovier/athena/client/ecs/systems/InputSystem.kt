package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.theovier.athena.client.ecs.components.Input
import com.theovier.athena.client.ecs.input
import com.theovier.athena.client.inputs.XboxInputAdapter
import ktx.ashley.allOf

class InputSystem : IteratingSystem(allOf(Input::class).get()), XboxInputAdapter  {
    private lateinit var input: Input
    private lateinit var controller: Controller

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        controller = Controllers.getCurrent()
        controller.addListener(this)
        input = engine.input
    }

    override fun removedFromEngine(engine: Engine) {
        super.removedFromEngine(engine)
        controller.removeListener(this)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val moveAxisHorizontal = controller.getAxis(XboxInputAdapter.AXIS_LEFT_X)
        val moveAxisVertical = -controller.getAxis(XboxInputAdapter.AXIS_LEFT_Y)
        val fireAxisValue = controller.getAxis(XboxInputAdapter.AXIS_TRIGGER_RIGHT)
        input.fire = fireAxisValue > XboxInputAdapter.FIRE_DEAD_ZONE
        input.movement.set(moveAxisHorizontal, moveAxisVertical)
    }

    override fun buttonDown(controller: Controller, buttonCode: Int): Boolean {
        when (buttonCode) {
            XboxInputAdapter.BUTTON_A -> input.hideHealthBars = !input.hideHealthBars
            else -> {
                return false
            }
        }
        return true
    }
}