package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.misc.spine.faceDirection
import ktx.ashley.allOf

class FacingSystem : IteratingSystem(allOf(Movement::class, Aim::class, Spine::class).get())  {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val movement = entity.movement
        val aim = entity.aim
        val skeleton = entity.spine.skeleton

        if (aim.isCurrentlyAiming) {
            skeleton.faceDirection(aim.direction)
        } else if (movement.hasMovementInput) {
            skeleton.faceDirection(movement.direction)
        }
    }
}