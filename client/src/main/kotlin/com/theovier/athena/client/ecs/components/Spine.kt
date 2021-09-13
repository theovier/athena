package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.esotericsoftware.spine.*
import ktx.ashley.get
import ktx.ashley.mapperFor
import com.esotericsoftware.spine.Skeleton

class Spine : Component {
    lateinit var skeleton: Skeleton
    lateinit var state: AnimationState

    fun playAnimationIfNotAlreadyPlaying(trackIndex: Int = 0, name: String, isLooping: Boolean = true) {
        val currentTrackEntry = state.getCurrent(trackIndex) ?: return
        val currentAnimation = currentTrackEntry.animation
        if (currentAnimation.name != name) {
            state.setAnimation(trackIndex, name, isLooping)
        }
    }

    companion object {
        val MAPPER = mapperFor<Spine>()
    }
}



val Entity.spine: Spine
    get() = this[Spine.MAPPER] ?: throw GdxRuntimeException("Spine for entity '$this' is null")