package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.movement.Direction
import com.theovier.athena.client.ecs.components.movement.direction
import com.theovier.athena.client.ecs.input
import com.theovier.athena.client.math.isNotZero
import ktx.ashley.allOf

class PlayerMovementSystem : IteratingSystem(allOf(Player::class, Transform::class, Direction::class).get()) {
    private lateinit var input: Input

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        input = engine.input
    }

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
