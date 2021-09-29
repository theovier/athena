package com.theovier.athena.client.ecs.systems.cleanup

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.PostponeAudio
import com.theovier.athena.client.ecs.components.Sound
import ktx.ashley.allOf
import ktx.ashley.exclude

class CleanupSoundSystem : IteratingSystem(allOf(Sound::class).exclude(PostponeAudio::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity.remove(Sound::class.java)
    }
}