package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.GdxRuntimeException
import com.esotericsoftware.spine.*
import ktx.ashley.get
import ktx.ashley.mapperFor
import com.esotericsoftware.spine.Skeleton
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class SkeletalAnimation : Component {
    var pathToAtlasFile = ""
    var pathToSkeletonFile = ""
    var initialAnimationName = "idle"
    var loopInitialAnimation = true
    var scale = 1/150f

    @Transient
    lateinit var skeleton: Skeleton

    @Transient
    lateinit var state: AnimationState

    fun build(): SkeletalAnimation {
        val atlasFile = Gdx.files.internal(pathToAtlasFile)
        val skeletonFile = Gdx.files.internal(pathToSkeletonFile)
        val atlas = TextureAtlas(atlasFile)
        val skeletonLoader = SkeletonJson(atlas)
        skeletonLoader.scale = scale
        val skeletonData = skeletonLoader.readSkeletonData(skeletonFile)
        val animationStateData = AnimationStateData(skeletonData)
        skeleton = Skeleton(skeletonData)
        state = AnimationState(animationStateData)
        state.setAnimation(0, initialAnimationName, loopInitialAnimation)
        return this
    }

    fun playAnimationIfNotAlreadyPlaying(trackIndex: Int = 0, name: String, isLooping: Boolean = true) {
        val currentTrackEntry = state.getCurrent(trackIndex)
        val currentAnimation = currentTrackEntry.animation
        if (currentAnimation.name != name) {
            state.setAnimation(trackIndex, name, isLooping)
        }
    }

    fun forceToFaceLeft() {
        if(skeleton.scaleX >= 0) {
            flipX()
        }
    }

    //if already flipped, revert the flip, otherwise don't do anything
    fun forceToFaceRight() {
        if(skeleton.scaleX < 0) {
            flipX()
        }
    }

    fun flipX() {
        skeleton.scaleX *= -1
    }

    companion object {
        val MAPPER = mapperFor<SkeletalAnimation>()
    }
}

val Entity.skeletalAnimation: SkeletalAnimation
    get() = this[SkeletalAnimation.MAPPER] ?: throw GdxRuntimeException("SkeletalAnimation for entity '$this' is null")