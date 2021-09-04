package com.theovier.athena.client.ecs.listeners

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.theovier.athena.client.ecs.components.physics

class PhysicsListener : EntityListener {

    override fun entityAdded(entity: Entity) {
        val physics = entity.physics
        val body = physics.body
        body.userData = entity
    }

    override fun entityRemoved(entity: Entity) {
        val physics = entity.physics
        val body = physics.body
        body.world.destroyBody(body)
    }
}