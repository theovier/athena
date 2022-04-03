package com.theovier.athena.client.ecs.systems.render

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.esotericsoftware.spine.SkeletonRenderer
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.animation.Spine
import com.theovier.athena.client.ecs.components.animation.spine
import com.theovier.athena.client.ecs.components.render.Invisible
import ktx.ashley.allOf
import ktx.ashley.exclude

class SpineRenderingSystem(private val batch: Batch) :
    SortedIteratingSystem(
        allOf(Spine::class, Transform::class).exclude(Invisible::class).get(),
        compareBy { it.transform }
    ) {
    private val renderer = SkeletonRenderer()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = entity.transform.position
        val skeleton = entity.spine.skeleton
        skeleton.setPosition(position.x, position.y)
        skeleton.updateWorldTransform()
        renderer.draw(batch, skeleton)
    }
}