package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Invincible

class InvincibleComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): Component {
        return Invincible()
    }
}