package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.ObjectMap
import com.theovier.athena.client.ecs.components.Transform
import mu.KotlinLogging

/**
 * see https://www.richardlord.net/blog/ecs/finite-state-machines-with-ash.html
 * This is a state machine for an entity. The state machine manages a set of states,
 * each of which has a set of component providers. When the state machine changes the state, it removes
 * components associated with the previous state and adds components associated with the new state.
 */
class EntityStateMachine(val entity: Entity) {
    private val log = KotlinLogging.logger {}
    private val states = mutableMapOf<String, EntityState>()
    private var currentState = EntityState()

    fun addState(name: String, state: EntityState): EntityStateMachine {
        states[name] = state
        return this
    }

    fun createState(name: String): EntityState {
        val state = EntityState()
        addState(name, state)
        return state
    }

    fun changeState(name: String) {
        val newState = states[name] ?: run {
            log.error { "Entity state '$name' does not exist. Ignoring state change." }
            return
        }
        if (newState == currentState) {
            return
        }
        changeComponents(newState)
        currentState = newState
    }

    private fun changeComponents(newState: EntityState) {
        val newProviders = ObjectMap(newState.providers)
        currentState.providers.forEach {
            val clazz = it.key
            val provider = it.value
            val newProvider = newProviders[clazz]
            if (provider.equals(newProvider)) {
                newProviders.remove(clazz) //components of next state are already present in the current state
            } else {
                entity.remove(clazz) //components needed in current state but not required in the next state
            }
        }
        addComponentsForNextState(newProviders)
    }

    private fun addComponentsForNextState(providers: ObjectMap<Class<out Component>, ComponentProvider<Component>>) {
        providers.forEach {
            val provider = it.value
            val component = provider.getComponent()
            entity.add(component)
        }
    }
}