package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.has
import ktx.ashley.mapperFor

class FiniteStateMachine : Component {
    var fsm = FiniteStateMachine()
    companion object {
        val MAPPER = mapperFor<FiniteStateMachine>()
    }
}

val Entity.finiteStateMachine: FiniteStateMachine
    get() = this[FiniteStateMachine.MAPPER] ?: throw GdxRuntimeException("FiniteStateMachine for entity '$this' is null")

val Entity.hasFiniteStateMachineComponent: Boolean
    get() = this.has(FiniteStateMachine.MAPPER)