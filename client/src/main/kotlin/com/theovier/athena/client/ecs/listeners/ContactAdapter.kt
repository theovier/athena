package com.theovier.athena.client.ecs.listeners

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold

open class ContactAdapter : ContactListener {
    override fun beginContact(contact: Contact) = Unit
    override fun endContact(contact: Contact) = Unit
    override fun preSolve(pcontact: Contact, oldManifold: Manifold) = Unit
    override fun postSolve(contact: Contact, impulse: ContactImpulse) = Unit
}