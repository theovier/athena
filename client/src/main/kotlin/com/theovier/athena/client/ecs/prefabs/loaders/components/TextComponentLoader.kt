package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Text

class TextComponentLoader : ComponentLoader {

    override fun load(componentJSON: JsonValue): Component {
        return Text().apply {
            text = componentJSON.getString("text", "")
        }
    }
}