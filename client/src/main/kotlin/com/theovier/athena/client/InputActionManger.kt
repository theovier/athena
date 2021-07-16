package com.theovier.athena.client

import com.badlogic.gdx.Input
import ktx.app.KtxInputAdapter
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

//https://xanadugame.ca/Making-Remappable-Controls/
class InputActionManger : KtxInputAdapter {

    private val actionListeners = ArrayList<ActionListener>()
    private val keyboardMappings: HashMap<Int, InputAction> = hashMapOf(
        Input.Keys.W to InputAction.MOVE_UP,
        Input.Keys.S to InputAction.MOVE_DOWN,
        Input.Keys.A to InputAction.MOVE_LEFT,
        Input.Keys.D to InputAction.MOVE_RIGHT
    )

    fun subscribe(listener: ActionListener) {
        actionListeners.add(0, listener) // Latest subscribers get priority
    }

    fun unsubscribe(listener: ActionListener) {
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