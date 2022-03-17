package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.ObjectMap

/**
 * Represents a state for an EntityStateMachine. The state contains any number of ComponentProviders which
 * are used to add components to the entity when this state is entered.
 */
class EntityState {
    val providers = ObjectMap<Class<out Component>, ComponentProvider<*>>()

    fun addProvider(type: Class<out Component>, provider: ComponentProvider<*>) {
        providers.put(type, provider)
    }

    fun <T: Component> add(type: Class<T>): StateComponentMapping {
        return StateComponentMapping(this, type)
    }

    fun <T: Component> get(type: Class<T>): ComponentProvider<*> {
        return providers.get(type)
    }

    fun has(type: Class<out Component>): Boolean {
        return providers.containsKey(type)
    }
}