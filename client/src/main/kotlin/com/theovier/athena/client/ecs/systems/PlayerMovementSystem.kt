package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.PlayerMovement
import ktx.ashley.allOf
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class PlayerMovementSystem : IteratingSystem(allOf(PlayerMovement::class).get()) {

    override fun processEntity(entity: Entity?, deltaTime: Float) {

    }
}