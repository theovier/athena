package com.theovier.athena.client.ecs.systems.render

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.theovier.athena.client.AthenaGame
import com.theovier.athena.client.ecs.components.render.TiledMap
import ktx.ashley.allOf
import ktx.ashley.get
import mu.KotlinLogging

class BackgroundRenderingSystem(private val camera: OrthographicCamera) : IteratingSystem(allOf(TiledMap::class).get()) {
    private val log = KotlinLogging.logger {}
    private var renderer: OrthogonalTiledMapRenderer? = null

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (entities.size() > 1) {
            log.error { "More than 1 entity with <TiledMap> component detected." }
        }
        if (renderer == null) {
            val component = entity.get<TiledMap>()
            this.renderer = OrthogonalTiledMapRenderer(component!!.map, AthenaGame.UNIT_SCALE)
        }
        renderer?.setView(camera)
        renderer?.render()
    }

    override fun removedFromEngine(engine: Engine) {
        super.removedFromEngine(engine)
        renderer?.dispose()
    }
}