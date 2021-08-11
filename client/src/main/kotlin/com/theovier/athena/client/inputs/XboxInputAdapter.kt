package com.theovier.athena.client.inputs

import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerListener
import kotlin.math.abs

interface XboxInputAdapter : ControllerListener {
    override fun connected(controller: Controller?) = Unit
    override fun disconnected(controller: Controller?) = Unit
    override fun buttonDown(controller: Controller?, buttonCode: Int): Boolean = false
    override fun buttonUp(controller: Controller?, buttonCode: Int): Boolean = false
    override fun axisMoved(controller: Controller?, axis: Int, value: Float): Boolean = false

    companion object {
        const val AXIS_LEFT_X = 0
        const val AXIS_LEFT_Y = 1
        const val AXIS_RIGHT_X = 2
        const val AXIS_RIGHT_Y = 3
        const val AXIS_TRIGGER_LEFT = 4
        const val AXIS_TRIGGER_RIGHT = 5
        const val MOVE_DEAD_ZONE = 0.15f
        const val LOOK_DEAD_ZONE = 0.55f
    }
}