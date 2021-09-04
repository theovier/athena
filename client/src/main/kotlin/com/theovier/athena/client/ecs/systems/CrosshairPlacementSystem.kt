package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.math.xy
import ktx.ashley.allOf
import ktx.math.minus
import ktx.math.plus
import ktx.math.times
import mu.KotlinLogging

private val log = KotlinLogging.logger {}
class CrosshairPlacementSystem(private val aim: Aim) : IteratingSystem(allOf(Crosshair::class, Movement::class, Transform::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (entities.size() > 1) {
            log.error { "More than 1 entity with <Crosshair> component detected." }
        }

        val transform = entity.transform
        val movement = entity.movement

        val offsetBetweenCurrentPositionAndTargetPosition = aim.targetPosition - transform.position.xy
        if (offsetBetweenCurrentPositionAndTargetPosition.len2() <= STANDING_STILL_THRESHOLD) {
            movement.direction = Vector2.Zero
        } else {
            movement.direction = offsetBetweenCurrentPositionAndTargetPosition.nor()
        }

        //non-physic movement
        if (movement.hasMovementInput) {
            movement.updateVelocity(deltaTime)
            transform.position.set(transform.position + movement.velocity * deltaTime)
        }
    }

    companion object {
        const val STANDING_STILL_THRESHOLD = 0.1f
    }
}