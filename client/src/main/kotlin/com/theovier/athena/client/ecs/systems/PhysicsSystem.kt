package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.World
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf
import java.lang.Math.min

class PhysicsSystem(private val world: World) : IteratingSystem(allOf(Transform::class, Physics::class).get()) {
    private var timeSinceLastUpdate = 0f

    override fun update(deltaTime: Float) {
        timeSinceLastUpdate += min(MINIMAL_TIME_STEP, deltaTime)
        while (timeSinceLastUpdate >= FIXED_TIME_STEP) {
            super.update(deltaTime)
            world.step(FIXED_TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
            timeSinceLastUpdate -= FIXED_TIME_STEP
        }
        val alpha = timeSinceLastUpdate / FIXED_TIME_STEP
        updateInterpolatedPosition(alpha)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val body = entity.physics.body
        transform.position.set(body.position, 0f)
    }

    private fun updateInterpolatedPosition(alpha: Float) {
        entities.forEach { entity ->
            val transform = entity.transform
            val body = entity.physics.body
            val previousPosition = transform.position
            val currentPosition = body.position
            transform.position.x = MathUtils.lerp(previousPosition.x, currentPosition.x, alpha)
            transform.position.y = MathUtils.lerp(previousPosition.y, currentPosition.y, alpha)
        }
    }

    companion object {
        const val MINIMAL_TIME_STEP = 1/30f
        const val FIXED_TIME_STEP = 1/60f
        const val VELOCITY_ITERATIONS = 6
        const val POSITION_ITERATIONS = 2
    }
}