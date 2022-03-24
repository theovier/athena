package com.theovier.athena.client.ecs.components.animation

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.esotericsoftware.spine.AnimationState
import com.theovier.athena.client.misc.spine.CustomAnimationStateAdapter
import com.tinder.StateMachine
import ktx.ashley.get
import ktx.ashley.has
import ktx.ashley.mapperFor

class WiggleAnimationController : Component, CustomAnimationStateAdapter {
    val stateMachine = StateMachine.create<State, Event, SideEffect> {
        initialState(State.Idle)
        state<State.Idle> {
            on<Event.OnTouched> {
                transitionTo(State.Wiggling)
            }
        }
        state<State.Wiggling> {
            on<Event.OnAnimationEnded> {
                transitionTo(State.Idle)
            }
        }
    }

    override fun complete(entry: AnimationState.TrackEntry) {
        stateMachine.transition(Event.OnAnimationEnded)
    }

    companion object {
        val MAPPER = mapperFor<WiggleAnimationController>()

        sealed class State {
            object Idle : State()
            object Wiggling : State()
        }

        sealed class Event {
            object OnTouched : Event()
            object OnAnimationEnded : Event()
        }

        sealed class SideEffect
    }
}

val Entity.wiggleAnimationController: WiggleAnimationController
    get() = this[WiggleAnimationController.MAPPER] ?: throw GdxRuntimeException("WiggleAnimationController component for entity '$this' is null")

val Entity.hasWiggleAnimationController: Boolean
    get() = this.has(WiggleAnimationController.MAPPER)