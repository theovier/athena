package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.theovier.athena.client.ecs.components.Input
import com.theovier.athena.client.inputs.XboxInputAdapter

class InputSystem(private val input: Input) : EntitySystem(), XboxInputAdapter  {
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

    override fun update(deltaTime: Float) {
        val moveAxisHorizontal = controller.getAxis(XboxInputAdapter.AXIS_LEFT_X)
        val moveAxisVertical = -controller.getAxis(XboxInputAdapter.AXIS_LEFT_Y)
        val fireAxisValue = controller.getAxis(XboxInputAdapter.AXIS_TRIGGER_RIGHT)
        input.fire = fireAxisValue > XboxInputAdapter.FIRE_DEAD_ZONE
        input.movement.set(moveAxisHorizontal, moveAxisVertical)
    }
}