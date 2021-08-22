package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.Animation
import com.theovier.athena.client.ecs.components.Skeleton
import com.theovier.athena.client.ecs.components.animation
import com.theovier.athena.client.ecs.components.skeleton
import ktx.ashley.allOf

class AnimationSystem : IteratingSystem(allOf(Animation::class, Skeleton::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val skeleton = entity.skeleton
        val animation = entity.animation

        if (animation.needsInitialization && skeleton.isInitialized) {
            val skeletonData = skeleton.skeleton!!.data
            animation.init(skeletonData)
        }

        animation.state?.update(deltaTime)
        animation.state?.apply(skeleton.skeleton)
    }
}