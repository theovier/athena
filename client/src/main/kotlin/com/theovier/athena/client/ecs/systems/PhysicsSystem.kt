package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d.World
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf

class PhysicsSystem(private val world: World) : IteratingSystem(allOf(Transform::class, Physics::class).get()) {

    override fun update(deltaTime: Float) {
        super.update(deltaTime)
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val body = entity.physics.body
        val currentPosition = body.position
        transform.position.set(currentPosition, 0f)
    }

    companion object {
        const val TIME_STEP = 1/60f
        const val VELOCITY_ITERATIONS = 6
        const val POSITION_ITERATIONS = 2
    }
}