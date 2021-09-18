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
        if (componentJSON.has(INITIAL_ANIMATION)) {
            loadInitialAnimation(componentJSON.get(INITIAL_ANIMATION), component.state)
        }
        if (componentJSON.has(DAMAGE_INDICATOR_SLOT_NAMES)) {
            val slotNames = loadDamageIndicatorSlotNames(componentJSON.get(DAMAGE_INDICATOR_SLOT_NAMES))
            component.damageIndicatorSlotNames = slotNames.toTypedArray()
        }
        return component
    }

    private fun loadInitialAnimation(animationJSON: JsonValue, state: AnimationState) {
        val name = animationJSON.getString(ANIMATION_NAME, DEFAULT_INITIAL_ANIMATION_NAME)
        val loop = animationJSON.getBoolean(IS_LOOP_ANIMATION, DEFAULT_LOOP_INITIAL_ANIMATION)
        state.setAnimation(0, name, loop)
    }

    private fun loadDamageIndicatorSlotNames(slotNamesJSON: JsonValue): List<String> {
        val slotNames = ArrayList<String>()
        val iterator = slotNamesJSON.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            slotNames.add(entry.asString())
        }
        return slotNames
    }

    companion object {
        private const val INITIAL_ANIMATION = "initialAnimation"
        private const val ANIMATION_NAME = "name"
        private const val DEFAULT_INITIAL_ANIMATION_NAME = "idle"
        private const val IS_LOOP_ANIMATION = "loop"
        private const val DEFAULT_LOOP_INITIAL_ANIMATION = true
        private const val DAMAGE_INDICATOR_SLOT_NAMES = "damageIndicatorSlotNames"
    }
}