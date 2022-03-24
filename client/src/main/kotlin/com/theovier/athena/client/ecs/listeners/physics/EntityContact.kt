package com.theovier.athena.client.ecs.listeners.physics

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture
import com.theovier.athena.client.ecs.components.Player
import ktx.ashley.has

data class EntityContact(val contact: Contact, val entityA: Entity, val entityB: Entity) {
    val fixtureA: Fixture = contact.fixtureA
    val fixtureB: Fixture = contact.fixtureB
    val hasSensor = fixtureA.isSensor || fixtureB.isSensor
    val position = contact.worldManifold.points.first()

    val playerEntityInvolved = entityTypeInvolved(Player.MAPPER)
    val noPlayerInvolved = !playerEntityInvolved

    fun <T: Component> entityTypeInvolved(mapper: ComponentMapper<T>): Boolean {
        return entityA.has(mapper) || entityB.has(mapper)
    }

    fun <T: Component> noEntityTypeInvolved(mapper: ComponentMapper<T>): Boolean {
        return !entityTypeInvolved(mapper)
    }
}