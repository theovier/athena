package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.theovier.athena.client.ecs.components.SpriteRenderer
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.renderer
import com.theovier.athena.client.ecs.components.transform
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.math.times
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class SpriteRenderingSystem (private val batch: Batch) :
    SortedIteratingSystem(
        allOf(Transform::class, SpriteRenderer::class).get(),
        compareBy { it[Transform.MAPPER] }
    ) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val renderer = entity.renderer

        if (renderer.sprite.texture == null) {
            log.error { "Entity '$entity' does not have a texture" }
            return
        }

        renderer.sprite.run {
            // normalize sprite to size (1,1) and scale sprite by the entity's size
            setScale(1 / width * transform.size.x, 1 / height * transform.size.y)
            setPosition(transform.position.x, transform.position.y)
            rotation = transform.rotationDegrees

            //put sprite to bottom left corner
            x -= originX * (1f - scaleX)
            y -= originY * (1f - scaleY)

            //center sprite horizontally
            x += (transform.size.x - width * scaleX) * 0.5f

            val scaledOffset = renderer.offset * transform.size
            x += scaledOffset.x
            y += scaledOffset.y

            draw(batch, batch.color.a)
        }
    }
}