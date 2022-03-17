package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component
import kotlin.reflect.KClass

/**
 * Used by the EntityState class to create the mappings of components to providers via a fluent interface.
 */
class StateComponentMapping(private val creatingState: EntityState, private val type: KClass<out Component>) {
    private lateinit var provider: ComponentProvider<Component>

    init {
        withType()
    }

    private fun setProvider(provider: ComponentProvider<Component>) {
        this.provider = provider
        creatingState.addProvider(type, provider)
    }

    fun withType(): StateComponentMapping {
        setProvider(ComponentTypeProvider(type))
        return this
    }

    fun <T: Component> withInstance(instance: T): StateComponentMapping {
        setProvider(ComponentInstanceProvider(instance))
        return this
    }

    fun <T: Component> withSingleton(type: KClass<T>): StateComponentMapping {
        setProvider(ComponentSingletonProvider(type))
        return this
    }

    fun withSingleton(): StateComponentMapping {
        setProvider(ComponentSingletonProvider(type))
        return this
    }

    fun <T: Component> withProvider(provider: ComponentProvider<T>): StateComponentMapping {
        setProvider(provider)
        return this
    }

    fun <T: Component> add(type: KClass<T>): StateComponentMapping {
        return creatingState.add(type)
    }
}