package com.theovier.athena.client.ecs.listeners

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Contact

class ProjectileCollisionListener(private val engine: Engine) : ContactAdapter() {

    override fun beginContact(contact: Contact) {
        val projectile: Entity
        val victim: Entity

        val a = contact.fixtureA.body
        val b = contact.fixtureB.body

        if (a.isBullet) {
            projectile = a.userData as Entity
            if (hitAnotherEntity(b)) {
                victim = b.userData as Entity
                onHit(projectile, victim)
            }
        } else if (b.isBullet) {
            projectile = b.userData as Entity
            if (hitAnotherEntity(a)) {
                victim = a.userData as Entity
                onHit(projectile, victim)
            }
        } else {
            return
        }
        engine.removeEntity(projectile)
    }

    private fun hitAnotherEntity(other: Any?): Boolean {
        return other != null && other is Entity
    }

    private fun onHit(projectile: Entity, victim: Entity) {
        //todo check for projectile component on entity
        //todo deal damage
    }
}