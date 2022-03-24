package com.theovier.athena.client.ecs.prefabs.loaders.components.animation

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Animation
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool
import com.theovier.athena.client.ecs.prefabs.loaders.components.ComponentLoader

class AnimationComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Animation {
        return Animation().apply {
            name = componentJSON.getString("name", Animation.DEFAULT_NAME)
        }
    }
}