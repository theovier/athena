package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.input
import ktx.ashley.allOf

class PlayerAimSystem : IteratingSystem(allOf(Aim::class, Player::class, Transform::class).get())  {
    private lateinit var input: Input

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        input = engine.input
    }

    override fun processEntity(player: Entity, deltaTime: Float) {
        val transform = player.transform
        val aimComponent = player.aim
        val aimInput = input.aim
        val isAiming = input.isAiming
        aimComponent.isCurrentlyAiming = isAiming

        if (isAiming) {
            val direction = aimInput.nor()
            player.aim.direction = direction
            transform.forward.set(direction)
        }
    }
}