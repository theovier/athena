package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.aim.Crosshair
import com.theovier.athena.client.ecs.components.aim.crosshair
import com.theovier.athena.client.ecs.components.movement.Direction
import com.theovier.athena.client.ecs.components.movement.direction
import com.theovier.athena.client.ecs.extensions.InputDrivenIteratingSystem
import com.theovier.athena.client.math.xy
import ktx.ashley.allOf
import ktx.math.minus
import ktx.math.plus
import ktx.math.times

class CrosshairPlacementSystem : InputDrivenIteratingSystem(allOf(Crosshair::class, Direction::class, Transform::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val direction = entity.direction
        val crosshair = entity.crosshair
        val crosshairOwner = crosshair.owner
        val ownerTransform = crosshairOwner.transform
        val ownerPosition = ownerTransform.position.xy
        val aimInput = input.aim

        val destinationRelativeToPlayer = aimInput * crosshair.maxDistanceToPlayer
        val destination = ownerPosition + destinationRelativeToPlayer

        val distanceBetweenCurrentPositionAndDestination = destination - transform.position.xy
        if (distanceBetweenCurrentPositionAndDestination.len2() <= Crosshair.STANDING_STILL_THRESHOLD) {
            direction.direction = Vector2.Zero
        } else {
            direction.direction = distanceBetweenCurrentPositionAndDestination.nor()
        }
    }
}