package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable

class PostponeAudio : Component, Poolable {
    override fun reset() = Unit
}