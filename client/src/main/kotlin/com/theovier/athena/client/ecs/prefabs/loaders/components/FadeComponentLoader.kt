package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Fade
import com.theovier.athena.client.ecs.components.Fade.Companion.DEFAULT_CURRENT
import com.theovier.athena.client.ecs.components.Fade.Companion.DEFAULT_DURATION
import com.theovier.athena.client.ecs.components.Fade.Companion.DEFAULT_END
import com.theovier.athena.client.ecs.components.Fade.Companion.DEFAULT_START
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class FadeComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Fade {
        return Fade().apply {
            start = componentJSON.getFloat("start", DEFAULT_START)
            end = componentJSON.getFloat("end", DEFAULT_END)
            current = componentJSON.getFloat("current", DEFAULT_CURRENT)
            duration = componentJSON.getFloat("duration", DEFAULT_DURATION)
        }
    }
}