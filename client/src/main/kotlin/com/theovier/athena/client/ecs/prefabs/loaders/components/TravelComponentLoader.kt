package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Travel

class TravelComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Component {
        return Travel().apply {
            origin.x = componentJSON.getFloat(ORIGIN_X, Travel.DEFAULT_ORIGIN_X)
            origin.y = componentJSON.getFloat(ORIGIN_Y, Travel.DEFAULT_ORIGIN_Y)
        }
    }

    companion object {
        const val ORIGIN_X = "x"
        const val ORIGIN_Y = "y"
    }
}