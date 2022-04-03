package com.theovier.athena.client.ecs.systems.damage

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.controllers.Controllers
import com.theovier.athena.client.ecs.components.damage.HitMarker
import ktx.ashley.allOf

class HapticDamageFeedbackSystem : IteratingSystem(allOf(HitMarker::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        Controllers.getCurrent()?.startVibration(250, 0.25f)
    }
}