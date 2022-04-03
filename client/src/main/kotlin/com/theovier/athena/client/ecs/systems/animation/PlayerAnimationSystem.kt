package com.theovier.athena.client.ecs.systems.animation

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.animation.Animation
import com.theovier.athena.client.ecs.components.animation.PlayerAnimationController
import com.theovier.athena.client.ecs.components.animation.PlayerAnimationController.Companion.Event
import com.theovier.athena.client.ecs.components.animation.PlayerAnimationController.Companion.State
import com.theovier.athena.client.ecs.components.animation.animation
import com.theovier.athena.client.ecs.components.animation.playerAnimationController
import com.theovier.athena.client.ecs.components.movement.Velocity
import com.theovier.athena.client.ecs.components.movement.velocity
import ktx.ashley.allOf

class PlayerAnimationSystem : IteratingSystem(allOf(Player::class, Animation::class, PlayerAnimationController::class, Velocity::class).get()) {

    override fun processEntity(player: Entity, deltaTime: Float) {
        val animation = player.animation
        val controller = player.playerAnimationController
        val velocity = player.velocity

        if (velocity.isNearlyStandingStillForAnimation) {
            controller.stateMachine.transition(Event.OnStoppedMoving)
        } else {
            controller.stateMachine.transition(Event.OnStartedMoving)
        }

        when(controller.stateMachine.state) {
            State.Idle -> animation.name = "idle"
            State.Running -> animation.name = "run"
        }
    }
}