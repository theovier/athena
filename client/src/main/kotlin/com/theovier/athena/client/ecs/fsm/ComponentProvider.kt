package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component

interface ComponentProvider<out T: Component> {
    fun getComponent(): T
    fun identifier(): Any
}