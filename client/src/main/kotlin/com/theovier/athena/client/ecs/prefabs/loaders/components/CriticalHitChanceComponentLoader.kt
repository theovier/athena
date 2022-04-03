package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.damage.CriticalHitChance
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class CriticalHitChanceComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): CriticalHitChance {
        return CriticalHitChance().apply {
            chance = componentJSON.getFloat("chance", CriticalHitChance.DEFAULT_CRIT_CHANCE)
        }
    }
}