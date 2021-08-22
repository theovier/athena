package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import com.esotericsoftware.spine.SkeletonJson
import com.esotericsoftware.spine.Skeleton as SpineSkeleton
import ktx.ashley.get
import ktx.ashley.mapperFor

class Skeleton : Component, Poolable {
    var skeleton: SpineSkeleton? = null
    var isInitialized = false
    val needsInitialization: Boolean
        get() = !isInitialized

    //todo only path and load later?
    var atlasFile = Gdx.files.internal("sprites/characters/dummy/dummy.atlas")
    var skeletonFile = Gdx.files.internal("sprites/characters/dummy/dummy.json")

    init {
        val atlas = TextureAtlas(atlasFile)
        val loader = SkeletonJson(atlas)
        val skeletonData = loader.readSkeletonData(skeletonFile)
        skeleton = SpineSkeleton(skeletonData)
        isInitialized = true
    }

    override fun reset() {
        skeleton = null
        isInitialized = false
    }

    companion object {
        val MAPPER = mapperFor<Skeleton>()
    }
}

val Entity.skeleton: Skeleton
    get() = this[Skeleton.MAPPER] ?: throw GdxRuntimeException("Skeleton for entity '$this' is null")