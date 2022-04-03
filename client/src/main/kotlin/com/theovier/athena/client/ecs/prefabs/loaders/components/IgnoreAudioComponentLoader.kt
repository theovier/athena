package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.audio.IgnoreAudio
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class IgnoreAudioComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): IgnoreAudio {
        return IgnoreAudio()
    }
}