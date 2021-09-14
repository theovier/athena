package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.utils.Pool.Poolable

class TiledMap : Component, Poolable {
    var map: TiledMap = TmxMapLoader().load(DEBUG_MAP)

    override fun reset() {
        map.dispose()
    }

    companion object {
        const val DEBUG_MAP = "maps/debug.tmx"
    }
}