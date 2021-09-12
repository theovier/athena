package com.theovier.athena.client.loaders.spine

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Array
import com.esotericsoftware.spine.SkeletonData
import com.esotericsoftware.spine.SkeletonJson

class SkeletonDataLoader(resolver: FileHandleResolver = InternalFileHandleResolver()) : AsynchronousAssetLoader<SkeletonData, SkeletonDataLoaderParameter>(resolver) {
    private var data: SkeletonData? = null

    override fun loadAsync(manager: AssetManager, fileName: String, file: FileHandle, parameter: SkeletonDataLoaderParameter) {
        val textureAtlasDescriptor = AssetDescriptor(parameter.atlasPath, TextureAtlas::class.java)
        val atlas = manager.get(textureAtlasDescriptor)
        val skeletonLoader = SkeletonJson(atlas).apply {
            scale = parameter.scale
        }
        data = skeletonLoader.readSkeletonData(file)
    }

    override fun loadSync(manager: AssetManager, fileName: String, file: FileHandle, parameter: SkeletonDataLoaderParameter): SkeletonData? {
        return data
    }

    override fun getDependencies(fileName: String, file: FileHandle, parameter: SkeletonDataLoaderParameter): Array<AssetDescriptor<*>> {
        val dependencies = Array<AssetDescriptor<*>>()
        val textureAtlasDependency = AssetDescriptor(parameter.atlasPath, TextureAtlas::class.java)
        dependencies.add(textureAtlasDependency)
        return dependencies
    }
}

class SkeletonDataLoaderParameter: AssetLoaderParameters<SkeletonData>() {
    var atlasPath = ""
    var scale = 1/150f
}