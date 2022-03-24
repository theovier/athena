package com.theovier.athena.client.ecs.components.animation

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.tinder.StateMachine
import ktx.ashley.get
import ktx.ashley.has
import ktx.ashley.mapperFor

class PlayerAnimationController : Component {
    val stateMachine = StateMachine.create<State, Event, SideEffect> {
        initialState(State.Idle)
        state<State.Idle> {
            on<Event.OnStartedMoving> {
                transitionTo(State.Running)
            }
        }
        state<State.Running> {
            on<Event.OnStoppedMoving> {
                transitionTo(State.Idle)
            }
        }
    }

    companion object {
        val MAPPER = mapperFor<PlayerAnimationController>()

        sealed class State {
            object Idle : State()
            object Running : State()
        }

        sealed class Event {
            object OnStartedMoving : Event()
            object OnStoppedMoving : Event()
        }

        sealed class SideEffect
    }
}

val Entity.playerAnimationController: PlayerAnimationController
    get() = this[PlayerAnimationController.MAPPER] ?: throw GdxRuntimeException("PlayerAnimationController component for entity '$this' is null")

val Entity.hasPlayerAnimationController: Boolean
    get() = this.has(PlayerAnimationController.MAPPER)