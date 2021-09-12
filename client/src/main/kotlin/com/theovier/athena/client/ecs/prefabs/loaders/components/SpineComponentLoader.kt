package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.utils.JsonValue
import com.esotericsoftware.spine.AnimationState
import com.esotericsoftware.spine.AnimationStateData
import com.esotericsoftware.spine.Skeleton
import com.theovier.athena.client.ecs.components.Spine
import com.theovier.athena.client.loaders.spine.AnimationStateDataLoaderParameter
import ktx.assets.async.AssetStorage
import ktx.assets.async.toIdentifier

class SpineComponentLoader(private val assets: AssetStorage) : ComponentLoader {
    override fun load(componentJSON: JsonValue): Spine {
        val component = Spine()
        val parameters = AnimationStateDataLoaderParameter().apply {
            skeletonPath = componentJSON.getString("skeleton")
            atlasPath = componentJSON.getString("atlas")
        }
        val descriptor = AssetDescriptor(parameters.skeletonPath, AnimationStateData::class.java)
        val data = assets.loadSync(descriptor.toIdentifier(), parameters)
        component.state = AnimationState(data)
        component.skeleton = Skeleton(data.skeletonData)
        return component
    }
}