package com.theovier.athena.client.ecs.systems.render

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.talosvfx.talos.runtime.render.SpriteBatchParticleRenderer
import com.theovier.athena.client.ecs.components.Particle
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.particle
import com.theovier.athena.client.ecs.components.transform
import ktx.ashley.allOf


class ParticleSystem(private val batch: Batch) : IteratingSystem(allOf(Particle::class, Transform::class).get()) {
    val defaultRenderer = SpriteBatchParticleRenderer(batch)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val particle = entity.particle

        val oldAlpha = batch.color.a

        particle.effect.run {
            val rotation = transform.rotationDegrees
            val offset = particle.offset
            val rotatedOffset = Vector2(offset.x, offset.y).rotateDeg(rotation)
            val originAdjustmentOffsetFromBox2DToLibgdx = 0.5f * transform.size.y //see the offset in the physics system
            val x = transform.position.x + rotatedOffset.x
            val y = transform.position.y + rotatedOffset.y + originAdjustmentOffsetFromBox2DToLibgdx
            setPosition(x, y)
            update(deltaTime)
            render(defaultRenderer)
        }
        batch.color.a = oldAlpha //todo find better way to use spriteBatchparticleRenderer
    }
}