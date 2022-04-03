package com.theovier.athena.client.ecs.systems.render

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.render.Foreground
import com.theovier.athena.client.ecs.components.render.Invisible
import com.theovier.athena.client.ecs.components.render.SpriteRenderer
import ktx.ashley.allOf
import ktx.ashley.exclude
import ktx.ashley.get

class ForegroundSpriteRenderingSystem(private val renderingSystem: SpriteRenderingSystem) :
    SortedIteratingSystem(
        allOf(Transform::class, SpriteRenderer::class, Foreground::class).exclude(Invisible::class).get(),
        compareBy { it[Transform.MAPPER] }
    ) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        renderingSystem.renderEntity(entity)
    }
}