package com.theovier.athena.client.loaders.spine

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Array
import com.esotericsoftware.spine.AnimationStateData
import com.esotericsoftware.spine.SkeletonData

class AnimationStateDataLoader(resolver: FileHandleResolver = InternalFileHandleResolver()) : AsynchronousAssetLoader<AnimationStateData, AnimationStateDataLoaderParameter>(resolver) {
    private var data: AnimationStateData? = null

    override fun loadAsync(manager: AssetManager, fileName: String, file: FileHandle, parameter: AnimationStateDataLoaderParameter) {
        val skeletonData = manager.get(parameter.skeletonPath, SkeletonData::class.java)
        data = AnimationStateData(skeletonData)
    }

    override fun loadSync(manager: AssetManager, fileName: String, file: FileHandle, parameter: AnimationStateDataLoaderParameter): AnimationStateData? {
        return data
    }

    override fun getDependencies(fileName: String, file: FileHandle, parameter: AnimationStateDataLoaderParameter): Array<AssetDescriptor<*>> {
        val dependencies = Array<AssetDescriptor<*>>()
        val skeletonDataLoaderParameter = SkeletonDataLoaderParameter().apply {
            atlasPath = parameter.atlasPath
            scale = parameter.scale
        }
        val skeletonDataLoaderDependency = AssetDescriptor(parameter.skeletonPath, SkeletonData::class.java, skeletonDataLoaderParameter)
        dependencies.add(skeletonDataLoaderDependency)
        return dependencies
    }
}

class AnimationStateDataLoaderParameter: AssetLoaderParameters<AnimationStateData>() {
    var skeletonPath = ""
    var atlasPath = ""
    var scale = 1/150f
}