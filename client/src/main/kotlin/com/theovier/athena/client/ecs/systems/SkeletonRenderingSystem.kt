package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.esotericsoftware.spine.SkeletonRenderer
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf

class SkeletonRenderingSystem(private val batch: Batch) : IteratingSystem(allOf(SkeletalAnimation::class, Transform::class).get()) {
    private val renderer = SkeletonRenderer()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = entity.transform.position
        val skeleton = entity.skeletonAnimation.skeleton
        skeleton.setPosition(position.x, position.y)
        skeleton.updateWorldTransform()
        renderer.draw(batch, skeleton)
    }
}