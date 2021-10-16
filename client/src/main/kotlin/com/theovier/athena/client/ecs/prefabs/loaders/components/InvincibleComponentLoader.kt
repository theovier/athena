package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Invincible
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class InvincibleComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Invincible {
        return Invincible()
    }
}