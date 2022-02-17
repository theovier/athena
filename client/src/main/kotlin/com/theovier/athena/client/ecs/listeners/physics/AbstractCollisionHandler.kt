package com.theovier.athena.client.ecs.listeners.physics

abstract class AbstractCollisionHandler : CollisionHandler {
    var next: CollisionHandler? = null
}