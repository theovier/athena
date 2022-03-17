package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.theovier.athena.client.ecs.fsm.EntityStateMachine
import ktx.ashley.get
import ktx.ashley.mapperFor

class StateMachine : Component {
    lateinit var fsm: EntityStateMachine
    companion object {
        val MAPPER = mapperFor<StateMachine>()
    }
}

val Entity.stateMachine: StateMachine
    get() = this[StateMachine.MAPPER] ?: throw GdxRuntimeException("StateMachine for entity '$this' is null")
