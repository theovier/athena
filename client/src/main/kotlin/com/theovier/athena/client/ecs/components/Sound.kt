package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import com.theovier.athena.client.ecs.components.Sound as SoundComponent
import ktx.ashley.mapperFor

class Sound : Component, Poolable {
    lateinit var sound: Sound
    var volume = DEFAULT_VOLUME

    override fun reset() {
        volume = DEFAULT_VOLUME
    }

    companion object {
        val MAPPER = mapperFor<SoundComponent>()
        const val DEFAULT_VOLUME = 1f
    }
}

val Entity.sound: SoundComponent
    get() = this[SoundComponent.MAPPER] ?: throw GdxRuntimeException("Sound for entity '$this' is null")