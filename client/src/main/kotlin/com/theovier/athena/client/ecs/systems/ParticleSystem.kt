package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.Particle
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.particle
import com.theovier.athena.client.ecs.components.transform
import ktx.ashley.allOf


class ParticleSystem(private val batch: Batch) : IteratingSystem(allOf(Particle::class, Transform::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val particle = entity.particle

        particle.effect.run {
            val rotation = transform.rotation
            val offset = particle.offset
            val rotatedOffset = Vector2(offset.x, offset.y).rotateDeg(rotation)

            setPosition(transform.position.x + rotatedOffset.x, transform.position.y + rotatedOffset.y)
            update(deltaTime)
            draw(batch)
        }
    }
}