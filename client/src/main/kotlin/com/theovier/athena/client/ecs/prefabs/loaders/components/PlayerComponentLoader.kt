package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Player

class PlayerComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Component {
        return Player()
    }
}