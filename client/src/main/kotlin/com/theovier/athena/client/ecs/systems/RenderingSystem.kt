package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import com.theovier.athena.client.ecs.components.SpriteRenderer
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.renderer
import com.theovier.athena.client.ecs.components.transformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class RenderingSystem (
    private val batch: Batch,
    private val viewport: Viewport,
) : SortedIteratingSystem(allOf(Transform::class, SpriteRenderer::class).get(), compareBy { it[Transform.mapper] }) {

    override fun update(deltaTime: Float) {
        forceSort()
        viewport.apply()
        batch.use(viewport.camera) {
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transformCmp = entity.transformComponent
        val renderCmp = entity.renderer

        if (renderCmp.sprite.texture == null) {
            log.error { "Entity '$entity' does not have a texture" }
            return
        }

        renderCmp.sprite.run {
            // normalize sprite to to size (1,1) and scale sprite by the entity's size
            setScale(1 / width * transformCmp.size.x, 1 / height * transformCmp.size.y)
            setPosition(transformCmp.position.x, transformCmp.position.y)


            // some explanation to the calculations below
            // origin is half the width and height of the sprite itself
            //
            // -> [origin * (1 - scale)]: puts the sprite correctly to the bottom left corner if scaling is applied
            // -> [(transformSizeX - width * scaleX) * 0.5]: centers the sprite horizontally within its bounding rectangle
            x -= originX * (1f - scaleX) + (transformCmp.size.x - width * scaleX) * 0.5f + renderCmp.offset.x
            y -= originY * (1f - scaleY) + renderCmp.offset.y

            // render entity
            draw(batch, batch.color.a)
        }
    }
}