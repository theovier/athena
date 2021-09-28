package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf
import ktx.math.plus
import ktx.math.times

class BulletShellEjectionSystem : IteratingSystem(allOf(Transform::class, Demo::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val shell = entity.getComponent(Demo::class.java)

        shell.velocityTime += deltaTime

        if (shell.velocity.len2() < shell.standingStillThreshold) {
            shell.velocity.set(Vector2(0f, 0f))
            entity.remove(Spin::class.java)
            entity.remove(Demo::class.java)
            return
        }

        if (transform.position.y <= shell.origin.y - 1f && shell.velocity.y < 0) {
            shell.velocity.y *= -0.25f
            shell.velocity.x *= 0.25f
            shell.velocityTime = 0f
        } else {
            shell.velocity.y += shell.acceleration * shell.velocityTime
        }

        //todo how to let this do the movement system?
        transform.position.set(transform.position + shell.velocity * deltaTime)
    }
}