package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.esotericsoftware.spine.SkeletonRenderer
import com.theovier.athena.client.ecs.components.Skeleton
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.skeleton
import com.theovier.athena.client.ecs.components.transform
import ktx.ashley.allOf

class SkeletonRenderingSystem(private val batch: Batch) : IteratingSystem(allOf(Skeleton::class, Transform::class).get()) {
    private val renderer = SkeletonRenderer()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val component = entity.skeleton
        val position = entity.transform.position
        component.skeleton?.setPosition(position.x, position.y)
        component.skeleton?.updateWorldTransform()
        renderer.draw(batch, component.skeleton)
    }
}