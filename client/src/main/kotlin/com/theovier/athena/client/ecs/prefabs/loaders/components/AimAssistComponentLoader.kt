package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.AimAssist
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class AimAssistComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): AimAssist {
        return AimAssist()
    }
}