package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.esotericsoftware.spine.AnimationState
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.fsm.EntityStateMachine
import com.theovier.athena.client.misc.spine.playAnimationIfNotAlreadyPlaying
import ktx.ashley.allOf

class PlayerAnimationSystem : IteratingSystem(allOf(Player::class, Spine::class, StateMachine::class).get()) {

    override fun processEntity(player: Entity, deltaTime: Float) {
        val spine = player.spine
        val animationState = spine.state
        val fsm = player.stateMachine.fsm
        playAnimation(animationState, fsm)
    }

    private fun playAnimation(animationState: AnimationState, fsm: EntityStateMachine) {
        when (fsm.currentState.name) {
            "idle" -> animationState.playAnimationIfNotAlreadyPlaying(name = "idle")
            "running" -> animationState.playAnimationIfNotAlreadyPlaying(name = "run")
        }
    }
}
