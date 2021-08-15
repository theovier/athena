package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.theovier.athena.client.ecs.components.Particle
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.particle
import com.theovier.athena.client.ecs.components.transform
import ktx.ashley.allOf

class ParticleSystem(private val batch: Batch) : IteratingSystem(allOf(Particle::class, Transform::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity.particle.effect.run {
            setPosition(entity.transform.position.x, entity.transform.position.y)
            update(deltaTime)
            draw(batch)
        }
    }
}