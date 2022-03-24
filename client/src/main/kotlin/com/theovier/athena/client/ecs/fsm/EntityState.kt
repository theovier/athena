package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.ObjectMap
import kotlin.reflect.KClass

/**
 * Represents a state for an EntityStateMachine. The state contains any number of ComponentProviders which
 * are used to add components to the entity when this state is entered.
 */
class EntityState {
    var name = DEFAULT_STATE_NAME
    val providers = ObjectMap<KClass<out Component>, ComponentProvider<Component>>()

    fun addProvider(type: KClass<out Component>, provider: ComponentProvider<Component>) {
        providers.put(type, provider)
    }

    fun <T: Component> add(type: KClass<T>): StateComponentMapping {
        return StateComponentMapping(this, type)
    }

    fun <T: Component> get(type: KClass<T>): ComponentProvider<Component> {
        return providers.get(type)
    }

    fun <T: Component> has(type: KClass<T>): Boolean {
        return providers.containsKey(type)
    }

    companion object {
        const val DEFAULT_STATE_NAME = "idle"
    }
}