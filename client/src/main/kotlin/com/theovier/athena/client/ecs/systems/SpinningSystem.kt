package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.Spin
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.spin
import com.theovier.athena.client.ecs.components.transform
import ktx.ashley.allOf

class SpinningSystem : IteratingSystem(allOf(Transform::class, Spin::class).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val spin = entity.spin
        transform.rotation += spin.speed * deltaTime
    }
}