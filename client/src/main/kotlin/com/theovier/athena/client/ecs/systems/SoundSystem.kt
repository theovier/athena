package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.sound
import com.theovier.athena.client.ecs.components.Sound as SoundComponent
import ktx.ashley.allOf

class SoundSystem : IteratingSystem(allOf(SoundComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val soundEffect = entity.sound.effect
        val sound = soundEffect.sound
        val volume = soundEffect.volume
        sound.play(volume)
    }
}