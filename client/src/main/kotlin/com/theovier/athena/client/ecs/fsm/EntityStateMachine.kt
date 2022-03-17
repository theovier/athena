package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.Player
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

        val componentsToAdd = mutableMapOf<Class<out Component>, ComponentProvider<*>>() //todo warum reicht nicht set<Component>? wegen removal?

        //todo dont like that chaining
        // get all component classes for new state
        newState.providers.keys().forEach { type ->
            componentsToAdd[type] = newState.providers[type]
        }




        //todo refactor me
        currentState.providers.keys().forEach { type ->
            val other = componentsToAdd[type]
            if (other != null && other.identifier() == currentState.providers[type].identifier()) {
                //remove components from toAdd if current state has same component as newState
                componentsToAdd.remove(type)
            } else {
                //remove components of current state if they are not in newState
                entity.remove(type)
            }
        }

        //todo extract method
        componentsToAdd.keys.forEach { type ->
            val component = componentsToAdd[type]?.getComponent()
            entity.add(component)
        }
        currentState = newState
    }
}