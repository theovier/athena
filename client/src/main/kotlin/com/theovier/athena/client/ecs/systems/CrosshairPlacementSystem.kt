package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.movement.Direction
import com.theovier.athena.client.ecs.components.movement.direction
import com.theovier.athena.client.inputs.XboxInputAdapter
import com.theovier.athena.client.math.xy
import ktx.ashley.allOf
import ktx.math.minus
import ktx.math.plus
import ktx.math.times

class CrosshairPlacementSystem : IteratingSystem(allOf(Crosshair::class, Direction::class, Transform::class).get()) {
    private lateinit var currentController: Controller

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        currentController = Controllers.getCurrent()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val direction = entity.direction
        val crosshair = entity.crosshair
        val crosshairOwner = crosshair.owner
        val ownerTransform = crosshairOwner.transform
        val ownerPosition = ownerTransform.position.xy
        val stickInput = pollProcessedInput()

        val destinationRelativeToPlayer = stickInput * crosshair.maxDistanceToPlayer
        val destination = ownerPosition + destinationRelativeToPlayer

        val distanceBetweenCurrentPositionAndDestination = destination - transform.position.xy
        if (distanceBetweenCurrentPositionAndDestination.len2() <= Crosshair.STANDING_STILL_THRESHOLD) {
            direction.direction = Vector2.Zero
        } else {
            direction.direction = distanceBetweenCurrentPositionAndDestination.nor()
        }
    }

    private fun pollInput(): Vector2 {
        val xAxisValueRaw = currentController.getAxis(XboxInputAdapter.AXIS_RIGHT_X)
        val yAxisValueRaw = -currentController.getAxis(XboxInputAdapter.AXIS_RIGHT_Y)
        return Vector2(xAxisValueRaw, yAxisValueRaw)
    }

    private fun pollProcessedInput(): Vector2 {
        var stickInput = pollInput()
        if (XboxInputAdapter.isAxisInputInDeadZone(stickInput)) {
            stickInput = Vector2.Zero
        }
        return stickInput
    }
}