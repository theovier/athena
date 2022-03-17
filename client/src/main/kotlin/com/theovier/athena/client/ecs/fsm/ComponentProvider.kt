package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component

//todo can do this without generics here?
interface ComponentProvider<out T: Component> {
    fun getComponent(): T
    fun identifier(): Any
}