package com.theovier.athena.client

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import ktx.app.KtxInputAdapter
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

//https://xanadugame.ca/Making-Remappable-Controls/
class InputActionManger : KtxInputAdapter {

    private val actionListeners = ArrayList<ActionListener>()
    private val keyboardMappings: HashMap<Int, InputAction> = hashMapOf(
        Input.Keys.W to InputAction.UP,
        Input.Keys.S to InputAction.DOWN,
        Input.Keys.A to InputAction.LEFT,
        Input.Keys.D to InputAction.RIGHT
    )

    fun subscribe(listener: ActionListener) {
        actionListeners.add(0, listener) // Latest subscribers get priority
    }

    fun unsubscribe(listener: ActionListener) {
        actionListeners.remove(listener)
    }

    fun init() {
        Gdx.input.inputProcessor = this
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