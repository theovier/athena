package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Health
import com.theovier.athena.client.ecs.components.HealthBar
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class HealthBarComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): HealthBar {
        val fillId = componentJSON.getString(FILL_REFERENCE)
        val healthId = componentJSON.getString(HEALTH_REFERENCE)
        val fillEntityReference = dependencyPool.entities[fillId]!!
        val healthComponentReference = dependencyPool.components[healthId]!! as Health

        return HealthBar().apply {
            fillReference = fillEntityReference
            healthReference = healthComponentReference
        }
    }

    companion object {
        const val FILL_REFERENCE = "fillReference"
        const val HEALTH_REFERENCE = "healthReference"
    }
}