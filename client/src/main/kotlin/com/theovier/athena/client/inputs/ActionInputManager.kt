package com.theovier.athena.client.inputs

import com.badlogic.gdx.Input
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class ActionInputManager : ActionInputAdapter {

    private val actionListeners = ArrayList<ActionInputListener>()
    private val keyboardMappings: HashMap<Int, ActionInput> = hashMapOf(
        Input.Keys.W to ActionInput.MOVE_UP,
        Input.Keys.S to ActionInput.MOVE_DOWN,
        Input.Keys.A to ActionInput.MOVE_LEFT,
        Input.Keys.D to ActionInput.MOVE_RIGHT
    )

    override fun subscribe(listener: ActionInputListener) {
        actionListeners.add(0, listener) // Latest subscribers get priority
    }

    override fun unsubscribe(listener: ActionInputListener) {
        actionListeners.remove(listener)
    }

    override fun keyDown(keycode: Int): Boolean {
        val action = keyboardMappings[keycode]
        action?.let {
            actionListeners.forEach {
                if (it.onAction(action)) {
                    return true
                }
            }
        } ?: log.debug { "No Action mapped to $keycode" }
        return false
    }
}