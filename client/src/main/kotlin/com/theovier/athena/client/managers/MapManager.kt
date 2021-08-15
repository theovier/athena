package com.theovier.athena.client.managers

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.Disposable
import com.theovier.athena.client.AthenaGame

class MapManager(private val camera: OrthographicCamera) : Renderable, Disposable {
    private val map = TmxMapLoader().load(DEBUG_MAP)
    private val renderer = OrthogonalTiledMapRenderer(map, AthenaGame.UNIT_SCALE)

    override fun render(deltaTime: Float) {
        renderer.setView(camera)
        renderer.render()
    }

    override fun dispose() {
        renderer.dispose()
        map.dispose()
    }

    companion object {
        const val DEBUG_MAP = "maps/debug.tmx"
    }
}