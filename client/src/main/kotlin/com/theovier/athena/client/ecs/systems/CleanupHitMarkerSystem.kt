package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.HitMarker
import ktx.ashley.allOf

class CleanupHitMarkerSystem : IteratingSystem(allOf(HitMarker::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity.remove(HitMarker::class.java)
    }
}