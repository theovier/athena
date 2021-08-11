package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.Crosshair
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.aim
import com.theovier.athena.client.ecs.components.transform
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.math.plus
import ktx.math.times
import mu.KotlinLogging

private val log = KotlinLogging.logger {}
class CrosshairSystem(private val player: Entity) : IteratingSystem(allOf(Crosshair::class, Transform::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (entities.size() > 1) {
            log.error { "More than 1 entity with <Crosshair> component detected." }
        }
        val crosshairRadius = entity[Crosshair.MAPPER]!!.radius
        val direction = player.aim.direction
        val origin = player.transform.position
        val destination = origin + direction * crosshairRadius

        entity.transform.position.set(destination)
    }
}