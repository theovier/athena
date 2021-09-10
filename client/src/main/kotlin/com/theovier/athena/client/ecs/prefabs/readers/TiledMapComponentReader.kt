package com.theovier.athena.client.ecs.prefabs.readers

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.TiledMap

class TiledMapComponentReader : ComponentReader {
    override fun read(componentJSON: JsonValue): Component {
        val component = TiledMap()
        val file = componentJSON.getString("file", TiledMap.DEBUG_MAP)
        component.map = TmxMapLoader().load(file)
        return component
    }
}