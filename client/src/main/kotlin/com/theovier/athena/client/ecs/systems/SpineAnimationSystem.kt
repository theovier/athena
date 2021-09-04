package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf

class SpineAnimationSystem : IteratingSystem(allOf(SpineAnimation::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val skeleton = entity.spineAnimation.skeleton
        val state = entity.spineAnimation.state
        state.update(deltaTime)
        state.apply(skeleton)
    }
}