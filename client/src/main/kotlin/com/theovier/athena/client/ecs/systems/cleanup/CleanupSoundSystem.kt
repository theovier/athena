package com.theovier.athena.client.ecs.systems.cleanup

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.Sound
import ktx.ashley.allOf

class CleanupSoundSystem : IteratingSystem(allOf(Sound::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity.remove(Sound::class.java)
    }
}