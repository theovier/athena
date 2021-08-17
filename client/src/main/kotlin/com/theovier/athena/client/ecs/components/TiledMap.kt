package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.utils.Pool.Poolable
import com.theovier.athena.client.ecs.prefabs.serializers.TiledMapSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable(with = TiledMapSerializer::class)
class TiledMap : Component, Poolable {

    @Contextual
    var map: TiledMap = TmxMapLoader().load(DEBUG_MAP)

    override fun reset() {
        map.dispose()
    }

    companion object {
        const val DEBUG_MAP = "maps/debug.tmx"
    }
}