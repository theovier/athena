package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import com.theovier.athena.client.ecs.components.SpriteRenderer
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.renderer
import com.theovier.athena.client.ecs.components.transform
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class RenderingSystem (
    private val batch: Batch,
    private val viewport: Viewport,
) : SortedIteratingSystem(allOf(Transform::class, SpriteRenderer::class).get(), compareBy { it[Transform.MAPPER] }) {
    override fun update(deltaTime: Float) {
        forceSort()
        viewport.apply()
        batch.use(viewport.camera) {
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val renderer = entity.renderer

        if (renderer.sprite.texture == null) {
            log.error { "Entity '$entity' does not have a texture" }
            return
        }

        renderer.sprite.run {
            // normalize sprite to to size (1,1) and scale sprite by the entity's size
            setScale(1 / width * transform.size.x, 1 / height * transform.size.y)
            setPosition(transform.position.x, transform.position.y)
            rotation = transform.rotation

            // -> [origin * (1 - scale)]: puts the sprite correctly to the bottom left corner if scaling is applied
            // -> [(transformSizeX - width * scaleX) * 0.5]: centers the sprite horizontally within its bounding rectangle
            x -= originX * (1f - scaleX) + (transform.size.x - width * scaleX) * 0.5f + renderer.offset.x
            y -= originY * (1f - scaleY) + renderer.offset.y
            draw(batch, batch.color.a)
        }
    }
}