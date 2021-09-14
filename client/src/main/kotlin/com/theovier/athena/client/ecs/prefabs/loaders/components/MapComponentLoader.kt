package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.TiledMap
import ktx.assets.async.AssetStorage

class MapComponentLoader(private val assets: AssetStorage) : ComponentLoader {
    override fun load(componentJSON: JsonValue): TiledMap {
        val component = TiledMap()
        val file = componentJSON.asString()
        component.map = assets.loadSync(file)
        return component
    }
}