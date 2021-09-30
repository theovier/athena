package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Foreground

class ForegroundComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Foreground {
        return Foreground()
    }
}