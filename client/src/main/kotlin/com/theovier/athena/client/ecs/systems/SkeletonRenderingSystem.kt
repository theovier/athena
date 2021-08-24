package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.esotericsoftware.spine.SkeletonRenderer
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf
import ktx.ashley.get

class SkeletonRenderingSystem(private val batch: Batch) :
    SortedIteratingSystem(
        allOf(SkeletalAnimation::class, Transform::class).get(),
        compareBy { it.transform }
    ) {
    private val renderer = SkeletonRenderer()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = entity.transform.position
        val skeleton = entity.skeletalAnimation.skeleton
        skeleton.setPosition(position.x, position.y)
        skeleton.updateWorldTransform()
        renderer.draw(batch, skeleton)
    }
}