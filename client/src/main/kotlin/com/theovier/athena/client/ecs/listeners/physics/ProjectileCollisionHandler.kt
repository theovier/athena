package com.theovier.athena.client.ecs.listeners.physics

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
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
        val contactPosition = contact.position

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
        onHit(projectile, victim, contactPosition)
    }

    private fun onHit(projectile: Entity, victim: Entity, contactPosition: Vector2) {
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

            if (victim.hasImpactComponent) {
                victim.impact.position = Vector3(contactPosition, 0f)
                victim.impact.angle = projectile.transform.rotation - MathUtils.PI //to turn radians 180 degree, subtract PI
            }
        }
        engine.removeEntity(projectile)
    }
}