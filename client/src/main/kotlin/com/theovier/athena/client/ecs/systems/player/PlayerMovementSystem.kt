package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.movement.Direction
import com.theovier.athena.client.ecs.components.movement.direction
import com.theovier.athena.client.ecs.extensions.InputDrivenIteratingSystem
import com.theovier.athena.client.math.isNotZero
import ktx.ashley.allOf

class PlayerMovementSystem : InputDrivenIteratingSystem(allOf(Player::class, Transform::class, Direction::class).get()) {

    override fun processEntity(player: Entity, deltaTime: Float) {
        val transform = player.transform
        val directionComponent = player.direction
        val movementDirection = input.movement
        directionComponent.direction = movementDirection
        if (movementDirection.isNotZero) {
            transform.forward.set(movementDirection)
        }
    }
}
