package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component
import kotlin.reflect.KClass

/**
 * This component provider always returns the same instance of the component. The instance
 * is created when first required and is of the type passed in to the constructor.
 */
class ComponentSingletonProvider<T: Component>(private val type: KClass<T>): ComponentProvider<T> {
    private val instance by lazy {
        type.java
            .getDeclaredConstructor()
            .newInstance()
    }

    override fun getComponent(): T {
        return instance
    }

    override fun identifier(): T {
        return getComponent()
    }

    override fun hashCode(): Int {
        return instance.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is ComponentSingletonProvider<*>
                && other.identifier() == identifier()
    }
}
