package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Timer
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class TimerComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Timer {
        return Timer().apply {
            millisSinceStart = componentJSON.getFloat(BEGIN_AT, Timer.DEFAULT_MILLIS_SINCE_START)
        }
    }

    companion object {
        const val BEGIN_AT = "beginAt"
    }
}