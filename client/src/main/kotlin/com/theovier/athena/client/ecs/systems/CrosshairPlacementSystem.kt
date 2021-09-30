package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.movement.Direction
import com.theovier.athena.client.ecs.components.movement.direction
import com.theovier.athena.client.math.xy
import ktx.ashley.allOf
import ktx.math.minus
import mu.KotlinLogging

private val log = KotlinLogging.logger {}
class CrosshairPlacementSystem(private val aim: Aim) : IteratingSystem(allOf(Crosshair::class, Direction::class, Transform::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (entities.size() > 1) {
            log.error { "More than 1 entity with <Crosshair> component detected." }
        }
        val transform = entity.transform
        val direction = entity.direction

        val offsetBetweenCurrentPositionAndTargetPosition = aim.targetPosition - transform.position.xy
        if (offsetBetweenCurrentPositionAndTargetPosition.len2() <= STANDING_STILL_THRESHOLD) {
            direction.direction = Vector2.Zero
        } else {
            direction.direction = offsetBetweenCurrentPositionAndTargetPosition.nor()
        }
    }

    companion object {
        const val STANDING_STILL_THRESHOLD = 0.1f
    }
}