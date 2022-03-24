package com.theovier.athena.client.ecs.listeners.physics

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Contact
import com.theovier.athena.client.ecs.isEntity

//todo add collision filtering: https://www.aurelienribon.com/post/2011-07-box2d-tutorial-collision-filtering
class WorldContactAdapter(engine: Engine) : ContactAdapter() {
    private val handler: CollisionHandler
    private val lootHandler = LootCollisionHandler()
    private val projectileHandler = ProjectileCollisionHandler(engine)
    private val wiggleHandler = WiggleCollisionHandler()

    init {
        lootHandler.next = projectileHandler
        projectileHandler.next = wiggleHandler
        handler = lootHandler
    }

    override fun beginContact(contact: Contact) {
        val fixtureA = contact.fixtureA
        val fixtureB = contact.fixtureB
        val a = fixtureA.body
        val b = fixtureB.body
        if (a.isEntity && b.isEntity) {
            val entityA = a.userData as Entity
            val entityB = b.userData as Entity
            val entityContact = EntityContact(contact, entityA, entityB)
            handler.handleCollision(entityContact)
        }
    }
}