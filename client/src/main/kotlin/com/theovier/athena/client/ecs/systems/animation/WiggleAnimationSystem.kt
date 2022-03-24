package com.theovier.athena.client.ecs.systems.animation

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.animation.WiggleAnimationController
import com.theovier.athena.client.ecs.components.animation.WiggleAnimationController.Companion.State
import com.theovier.athena.client.ecs.components.animation.wiggleAnimationController
import ktx.ashley.allOf

class WiggleAnimationSystem : IteratingSystem(allOf(WiggleAnimationController::class, Animation::class, Spine::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val animation = entity.animation
        val animationState = entity.spine.state
        val controller = entity.wiggleAnimationController
        when(controller.stateMachine.state) {
            State.Idle -> {
                animationState.removeListener(controller)
                animation.name = "idle"
            }
            State.Wiggling -> animation.name = "move"
        }
    }
}