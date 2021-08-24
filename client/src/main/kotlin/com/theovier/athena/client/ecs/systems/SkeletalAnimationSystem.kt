package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.esotericsoftware.spine.SkeletonRenderer
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf

class SkeletalAnimationSystem : IteratingSystem(allOf(SkeletalAnimation::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val skeleton = entity.skeletalAnimation.skeleton
        val state = entity.skeletalAnimation.state
        state.update(deltaTime)
        state.apply(skeleton)
    }
}