package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool
import com.esotericsoftware.spine.*
import ktx.ashley.get
import ktx.ashley.mapperFor
import com.esotericsoftware.spine.Skeleton as SpineSkeleton

class SkeletonAnimation : Component, Pool.Poolable {
    var atlasPath = "sprites/characters/dummy/dummy.atlas"
    var skeletonPath = "sprites/characters/dummy/dummy.json"

    private val atlasFile = Gdx.files.internal(atlasPath)
    private val skeletonFile = Gdx.files.internal(skeletonPath)
    private val atlas = TextureAtlas(atlasFile)
    private val skeletonLoader = SkeletonJson(atlas)
    private val skeletonData = skeletonLoader.readSkeletonData(skeletonFile)
    private val animationStateData = AnimationStateData(skeletonData)

    val skeleton = SpineSkeleton(skeletonData)
    val state = AnimationState(animationStateData)

    override fun reset() {

    }

    companion object {
        val MAPPER = mapperFor<SkeletonAnimation>()
    }
}

val Entity.skeletonAnimation: SkeletonAnimation
    get() = this[SkeletonAnimation.MAPPER] ?: throw GdxRuntimeException("SkeletonAnimation for entity '$this' is null")