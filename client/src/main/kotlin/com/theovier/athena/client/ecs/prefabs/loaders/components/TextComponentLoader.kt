package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Text
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class TextComponentLoader : ComponentLoader {

    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Text {
        return Text().apply {
            text = componentJSON.getString("text", "")
        }
    }
}