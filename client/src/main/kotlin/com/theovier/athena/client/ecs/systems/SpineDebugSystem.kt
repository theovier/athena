package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.esotericsoftware.spine.SkeletonRendererDebug
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf

class SpineDebugSystem(private val camera: Camera) : IteratingSystem(allOf(SpineAnimation::class).get()) {
    private val renderer = SkeletonRendererDebug().apply {
        setScale(1/100f)
        setRegionAttachments(false)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val skeleton = entity.spineAnimation.skeleton
        renderer.shapeRenderer.projectionMatrix = camera.combined
        renderer.draw(skeleton)
    }
}