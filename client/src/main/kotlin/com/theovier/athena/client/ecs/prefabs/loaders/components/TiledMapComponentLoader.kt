package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.TiledMap

class TiledMapComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue): TiledMap {
        val component = TiledMap()
        val file = componentJSON.asString()
        component.map = TmxMapLoader().load(file)
        return component
    }
}