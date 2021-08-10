package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import kotlinx.serialization.Serializable

/**
 * There should only be 1 entity with this component at a time.
 * Marks its entity so the camera follows it.
 */
@Serializable
class CameraTarget : Component, Poolable {
    override fun reset() = Unit
}