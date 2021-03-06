package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component
import kotlin.reflect.KClass

/**
 * This component provider always returns a new instance of a component. An instance
 * is created when requested and is of the type passed in to the constructor.
 */
class ComponentTypeProvider<T: Component>(private val type: KClass<T>): ComponentProvider<T> {

    override fun getComponent(): T {
        return type.java
            .getDeclaredConstructor()
            .newInstance()
    }

    override fun identifier(): Any {
        return type
    }

    override fun hashCode(): Int {
        return type.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is ComponentTypeProvider<*>
                && other.identifier() == identifier()
    }
}
