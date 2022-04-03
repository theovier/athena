package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.animation.Wiggle
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class WiggleComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Wiggle {
        return Wiggle()
    }
}