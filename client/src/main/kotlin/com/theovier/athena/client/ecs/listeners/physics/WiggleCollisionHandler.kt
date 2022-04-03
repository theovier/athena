package com.theovier.athena.client.ecs.listeners.physics

import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.animation.Wiggle
import com.theovier.athena.client.ecs.components.animation.spine
import com.theovier.athena.client.ecs.components.animation.WiggleAnimationController
import com.theovier.athena.client.ecs.components.animation.hasWiggleAnimationController
import com.theovier.athena.client.ecs.components.animation.wiggleAnimationController
import com.theovier.athena.client.ecs.components.animation.hasSpineComponent
import ktx.ashley.has

class WiggleCollisionHandler : AbstractCollisionHandler() {

    override fun handleCollision(contact: EntityContact) {
        val wigglyEntity: Entity
        val entityA = contact.entityA
        val entityB = contact.entityB

        if (contact.noEntityTypeInvolved(Wiggle.MAPPER) || contact.noPlayerInvolved) {
            next?.handleCollision(contact)
            return
        }

        wigglyEntity = if (entityA.has(Wiggle.MAPPER)) {
            entityA
        } else {
            entityB
        }
        handleWiggle(wigglyEntity)
    }

    private fun handleWiggle(wigglyEntity: Entity) {
        if (wigglyEntity.hasWiggleAnimationController && wigglyEntity.hasSpineComponent) {
            val animationState = wigglyEntity.spine.state
            val controller = wigglyEntity.wiggleAnimationController
            animationState.addListener(controller)
            controller.stateMachine.transition(WiggleAnimationController.Companion.Event.OnTouched)
        }
    }
}