package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.animation.Spine
import com.theovier.athena.client.ecs.components.animation.spine
import com.theovier.athena.client.ecs.extensions.InputDrivenIteratingSystem
import com.theovier.athena.client.misc.spine.faceDirection
import ktx.ashley.allOf

class PlayerFacingSystem : InputDrivenIteratingSystem(allOf(Player::class, Transform::class, Spine::class).get())  {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val skeleton = entity.spine.skeleton

        //enables walking backwards and aiming in the opposite direction
        if (input.isAiming) {
            skeleton.faceDirection(input.aim)
        } else {
            skeleton.faceDirection(transform.forward)
        }
    }
}