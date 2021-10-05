package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.HealthBar

class HealthBarComponentLoader : ComponentLoader {

    override fun load(componentJSON: JsonValue): HealthBar {
        //todo can not access parent / children here.
        return HealthBar()
    }
}