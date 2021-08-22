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

class SkeletalAnimation : Component {
    var pathToAtlasFile = ""
    var pathToSkeletonFile = ""
    var scale = 1/150f

    lateinit var skeleton: Skeleton
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
        return this
    }

    fun setAnimation(trackIndex: Int, animationName: String, loop: Boolean) {
        state.setAnimation(trackIndex, animationName, loop)
    }

    companion object {
        val MAPPER = mapperFor<SkeletalAnimation>()
    }
}

val Entity.skeletonAnimation: SkeletalAnimation
    get() = this[SkeletalAnimation.MAPPER] ?: throw GdxRuntimeException("SkeletonAnimation for entity '$this' is null")