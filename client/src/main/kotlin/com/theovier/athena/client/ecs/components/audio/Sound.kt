package com.theovier.athena.client.ecs.components.audio

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.theovier.athena.client.misc.audio.SoundEffect
import ktx.ashley.get
import com.theovier.athena.client.ecs.components.audio.Sound as SoundComponent
import ktx.ashley.mapperFor

class Sound : Component {
    lateinit var effect: SoundEffect

    companion object {
        val MAPPER = mapperFor<SoundComponent>()
        const val DEFAULT_VOLUME = 1f
    }
}

val Entity.sound: SoundComponent
    get() = this[SoundComponent.MAPPER] ?: throw GdxRuntimeException("Sound for entity '$this' is null")