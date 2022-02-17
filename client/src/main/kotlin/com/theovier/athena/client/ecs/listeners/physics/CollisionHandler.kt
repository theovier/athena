package com.theovier.athena.client.ecs.listeners.physics

interface CollisionHandler {
    fun handleCollision(contact: EntityContact)
}