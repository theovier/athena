package com.theovier.athena.client.ecs.components.audio

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable

// used when you want to preload audio effects but not play them directly
class IgnoreAudio : Component, Poolable {
    override fun reset() = Unit
}