package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.render.Text
import com.theovier.athena.client.ecs.components.render.text
import ktx.ashley.allOf

class FadeTextSystem : IteratingSystem(allOf(Text::class, Fade::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val fadeComponent = entity.fade
        val textComponent = entity.text
        textComponent.color.a = fadeComponent.current
    }
}