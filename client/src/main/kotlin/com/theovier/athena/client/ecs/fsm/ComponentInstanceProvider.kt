package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component

/**
 * This component provider always returns the same instance of the component. The instance
 * is passed to the provider at initialisation.
 */
class ComponentInstanceProvider<T: Component>(private val instance: T): ComponentProvider<T> {

    override fun getComponent(): T {
        return instance
    }

    override fun identifier(): T {
        return instance
    }
}
