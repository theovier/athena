package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector3
import com.theovier.athena.client.ecs.components.CameraTarget
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.transform
import ktx.ashley.allOf
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class CameraMovementSystem(private val camera: Camera, private val startPosition: Vector3 = Vector3.Zero) : IteratingSystem(allOf(CameraTarget::class, Transform::class).get()) {
    private var timeScale = 1 // 0 = Paused; 0.1f = slow motion
    private val proximityPace = 0.01f // each frame get this % closer to our target

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        camera.position.set(startPosition)
        log.debug { camera.position }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        log.debug { camera.position }

        if (entities.size() > 1) {
            log.error { "More than 1 entity with <CameraLock> component detected." }
        }

        with(entity.transform.position) {
            // x+= (target - x) * 0.1f
            camera.position.x += (x - camera.position.x) * proximityPace * timeScale
            camera.position.y += (y - camera.position.y) * proximityPace * timeScale
        }
        camera.update()
    }
}