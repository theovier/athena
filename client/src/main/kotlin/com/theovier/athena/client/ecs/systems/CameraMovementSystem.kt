package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.theovier.athena.client.ecs.components.CameraTarget
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.transform
import ktx.ashley.allOf
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class CameraMovementSystem(private val camera: Camera) : IteratingSystem(allOf(CameraTarget::class, Transform::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (entities.size() > 1) {
            log.error { "More than 1 entity with <CameraLock> component detected." }
        }
        with(entity.transform.position) {
            camera.position.set(x, y, 0f)
        }
        camera.update()
    }
}