package com.theovier.athena.client.ecs.listeners.physics

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Contact
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.Damage as DamageComponent
import ktx.ashley.has

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
        }
    }

    private fun hitAnotherEntity(other: Body): Boolean {
        return other.userData != null && other.userData is Entity
    }

    private fun onHit(projectile: Entity, victim: Entity) {
        if (projectile.has(DamageComponent.MAPPER)) {
            val damage = projectile.damageComponent.damage
            val isSelfHit = damage.source?.owner == victim
            if (isSelfHit) {
                return
            }
            if (victim.hasNoHitMarkerComponent) {
                victim.add(HitMarker())
            }
            victim.hitmarker.onHit(damage)
        }
        engine.removeEntity(projectile)
    }
}