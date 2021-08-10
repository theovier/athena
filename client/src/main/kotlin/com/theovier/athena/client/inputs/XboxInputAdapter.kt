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
        const val DEADZONE = 0.15

        fun isAxisValueInDeadzone(value: Float): Boolean {
            return abs(value) <= DEADZONE
        }
    }
}