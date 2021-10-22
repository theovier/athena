package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.DustTrail
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class DustTrailComponentLoader : ComponentLoader {

    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): DustTrail {
        return DustTrail().apply {
            spawnFrequency = componentJSON.getFloat(FREQUENCY, DustTrail.DEFAULT_SPAWN_FREQUENCY_IN_SECONDS)
        }
    }

    companion object {
        const val FREQUENCY = "frequency"
    }
}