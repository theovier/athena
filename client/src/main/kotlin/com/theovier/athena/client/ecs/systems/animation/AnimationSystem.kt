package com.theovier.athena.client.ecs.systems.animation

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.animation.*
import com.theovier.athena.client.misc.spine.playAnimationIfNotAlreadyPlaying
import ktx.ashley.allOf

class AnimationSystem : IteratingSystem(allOf(Spine::class, Animation::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val spine = entity.spine
        val animation = entity.animation
        val animationState = spine.state
        animationState.playAnimationIfNotAlreadyPlaying(name = animation.name, isLooping = animation.looping)
    }
}