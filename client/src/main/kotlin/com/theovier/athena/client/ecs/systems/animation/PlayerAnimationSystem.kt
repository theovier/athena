package com.theovier.athena.client.ecs.systems.animation

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.movement.Velocity
import com.theovier.athena.client.ecs.components.movement.velocity
import com.theovier.athena.client.misc.spine.playAnimationIfNotAlreadyPlaying
import ktx.ashley.allOf

class PlayerAnimationSystem : IteratingSystem(allOf(Player::class, StateMachine::class, Velocity::class).get()) {

    override fun processEntity(player: Entity, deltaTime: Float) {
        val velocity = player.velocity
        val fsm = player.stateMachine.fsm

        if (velocity.isNearlyStandingStillForAnimation) {
            fsm.changeState("idle")
        } else {
            fsm.changeState("running")
        }
    }
}