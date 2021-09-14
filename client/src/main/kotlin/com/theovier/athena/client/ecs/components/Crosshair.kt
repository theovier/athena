package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable

//used to identify the crosshair entity
class Crosshair : Component, Poolable {
    override fun reset() = Unit
}