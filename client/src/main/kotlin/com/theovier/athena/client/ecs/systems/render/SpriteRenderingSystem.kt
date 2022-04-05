package com.theovier.athena.client.ecs.systems.render

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.render.*
import ktx.ashley.allOf
import ktx.ashley.exclude
import ktx.ashley.get
import ktx.math.times
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class SpriteRenderingSystem(private val batch: Batch) :
    SortedIteratingSystem(
        allOf(Transform::class, SpriteRenderer::class)
            .exclude(Foreground::class)
            .exclude(Invisible::class)
            .exclude(WorldSpaceUI::class)
            .get(),
        compareBy { it[Transform.MAPPER] }
    ) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        renderEntity(entity, batch)
    }

    companion object {
        fun renderEntity(entity: Entity, batch: Batch) {
            val transform = entity.transform
            val renderer = entity.renderer
            val position = transform.position
            val size = transform.size

            if (renderer.sprite.texture == null) {
                log.error { "Entity '$entity' does not have a texture" }
                return
            }

            renderer.sprite.run {
                // normalize sprite to size (1,1) and scale sprite by the entity's size
                setScale(1 / width * size.x, 1 / height * size.y)
                setPosition(position.x, position.y)
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
}