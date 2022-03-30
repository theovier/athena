package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.extensions.InputDrivenIteratingSystem
import ktx.ashley.allOf

class PlayerAimSystem : InputDrivenIteratingSystem(allOf(Aim::class, Player::class, Transform::class).get())  {

    override fun processEntity(player: Entity, deltaTime: Float) {
        val transform = player.transform
        val aimComponent = player.aim
        val aimInput = input.aim
        val isAiming = input.isAiming
        aimComponent.isCurrentlyAiming = isAiming

        if (isAiming) {
            val direction = aimInput.nor()
            aimComponent.direction = direction
            transform.forward.set(direction)
        }
    }
}