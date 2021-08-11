package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
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
        val crosshairMinRadius = entity[Crosshair.MAPPER]!!.minRadius
        val crosshairMaxRadius = entity[Crosshair.MAPPER]!!.maxRadius

        val direction = player.aim.direction
        val origin = player.transform.position
        val distance = MathUtils.clamp(player.aim.distanceToAimTarget, crosshairMinRadius, crosshairMaxRadius)
        val destination = origin + direction * distance

        entity.transform.position.set(destination)
    }
}