package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.IgnoreAudio

class IgnoreAudioComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): IgnoreAudio {
        return IgnoreAudio()
    }
}