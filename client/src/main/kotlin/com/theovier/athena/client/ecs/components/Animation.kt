package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import com.esotericsoftware.spine.AnimationState
import com.esotericsoftware.spine.AnimationStateData
import com.esotericsoftware.spine.SkeletonData
import ktx.ashley.get
import ktx.ashley.mapperFor

class Animation : Component, Poolable {
    var state: AnimationState? = null
    var isInitialized = false
    val needsInitialization: Boolean
        get() = !isInitialized

    override fun reset() {
        state = null
        isInitialized = false
    }

    fun init(skeletonData: SkeletonData) {
        val animationStateData = AnimationStateData(skeletonData)
        state = AnimationState(animationStateData)

        //todo how to control this from outside?
        state?.setAnimation(0, "idle", true)
        isInitialized = true
    }

    companion object {
        val MAPPER = mapperFor<Animation>()
    }
}

val Entity.animation: Animation
    get() = this[Animation.MAPPER] ?: throw GdxRuntimeException("Animation for entity '$this' is null")