package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.PostponeAudio

class PostponeAudioComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): PostponeAudio {
        return PostponeAudio()
    }
}