package com.theovier.athena.client.ecs.components.render

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable

class Foreground : Component, Poolable {
    override fun reset() = Unit
}