package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Trauma

class TraumaComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Trauma {
        return Trauma().apply {
            maxTranslationalOffset = componentJSON.getFloat(MAX_TRANSLATIONAL_OFFSET, Trauma.DEFAULT_MAX_TRANSLATIONAL_OFFSET)
            maxRotationalOffset = componentJSON.getFloat(MAX_ROTATIONAL_OFFSET, Trauma.DEFAULT_MAX_ROTATIONAL_OFFSET)
            reductionFactor = componentJSON.getFloat(REDUCTION_FACTOR, Trauma.DEFAULT_REDUCTION_FACTOR)
        }
    }

    companion object {
        const val MAX_TRANSLATIONAL_OFFSET = "maxOffsetTranslation"
        const val MAX_ROTATIONAL_OFFSET = "maxOffsetRotation"
        const val REDUCTION_FACTOR = "reductionFactor"
    }
}