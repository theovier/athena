package com.theovier.athena.client.ecs.systems.render

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.render.Foreground
import com.theovier.athena.client.ecs.components.render.Invisible
import com.theovier.athena.client.ecs.components.render.SpriteRenderer
import ktx.ashley.allOf
import ktx.ashley.exclude
import ktx.ashley.get

class ForegroundSpriteRenderingSystem(private val batch: Batch) :
    SortedIteratingSystem(
        allOf(Transform::class, SpriteRenderer::class, Foreground::class).exclude(Invisible::class).get(),
        compareBy { it[Transform.MAPPER] }
    ) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        SpriteRenderingSystem.renderEntity(entity, batch)
    }
}