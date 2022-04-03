package com.theovier.athena.client.ecs.systems.render

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.esotericsoftware.spine.SkeletonRendererDebug
import com.theovier.athena.client.ecs.components.animation.Spine
import com.theovier.athena.client.ecs.components.animation.spine
import ktx.ashley.allOf

class SpineDebugSystem(private val camera: Camera) : IteratingSystem(allOf(Spine::class).get()) {
    private val renderer = SkeletonRendererDebug().apply {
        setScale(1/100f)
        setRegionAttachments(false)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val skeleton = entity.spine.skeleton
        renderer.shapeRenderer.projectionMatrix = camera.combined
        renderer.draw(skeleton)
    }
}