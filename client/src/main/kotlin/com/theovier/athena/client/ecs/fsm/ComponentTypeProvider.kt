package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component

/**
 * This component provider always returns a new instance of a component. An instance
 * is created when requested and is of the type passed in to the constructor.
 */
class ComponentTypeProvider<T: Component>(private val type: Class<T>): ComponentProvider<T> {

    override fun getComponent(): T {
        return type
            .getDeclaredConstructor()
            .newInstance()
    }

    override fun identifier(): Any {
        return type
    }
}
