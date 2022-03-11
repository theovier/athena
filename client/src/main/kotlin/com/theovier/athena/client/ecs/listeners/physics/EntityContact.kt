package com.theovier.athena.client.ecs.listeners.physics

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture

data class EntityContact(val contact: Contact, val entityA: Entity, val entityB: Entity) {
    val fixtureA: Fixture = contact.fixtureA
    val fixtureB: Fixture = contact.fixtureB
    val hasSensor = fixtureA.isSensor || fixtureB.isSensor
    val position = contact.worldManifold.points.first()
}