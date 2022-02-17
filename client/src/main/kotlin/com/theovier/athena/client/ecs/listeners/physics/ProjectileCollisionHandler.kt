package com.theovier.athena.client.ecs.listeners.physics

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.isEntity
import ktx.ashley.has

class ProjectileCollisionHandler(private val engine: Engine) : AbstractCollisionHandler() {

    override fun handleCollision(contact: EntityContact) {
        if (contact.hasSensor) {
            next?.handleCollision(contact)
            return
        }

        val projectile: Entity
        val victim: Entity
        val fixtureA = contact.fixtureA
        val fixtureB = contact.fixtureB
        val a = fixtureA.body
        val b = fixtureB.body

        if (!a.isBullet && !b.isBullet) {
            next?.handleCollision(contact)
            return
        }

        if (a.isBullet) {
            projectile = contact.entityA
            victim = contact.entityB
        } else {
            projectile = contact.entityB
            victim =  contact.entityA
        }
        onHit(projectile, victim)
    }

    private fun onHit(projectile: Entity, victim: Entity) {
        if (projectile.has(Damage.MAPPER)) {
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