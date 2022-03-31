package com.theovier.athena.client.ecs.systems.spawn

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.movement.Acceleration
import com.theovier.athena.client.ecs.components.movement.Velocity
import com.theovier.athena.client.ecs.components.movement.acceleration
import com.theovier.athena.client.ecs.components.movement.velocity
import ktx.ashley.allOf

class BulletShellEjectionSystem : IteratingSystem(
    allOf(Transform::class, Velocity::class, Acceleration::class, Timer::class, Travel::class).get()
) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val velocity = entity.velocity
        val acceleration = entity.acceleration
        val timer = entity.timer
        val travel = entity.travel

        if (velocity.isNearlyStandingStill) {
            entity.remove(Spin::class.java)
            entity.remove(Velocity::class.java)
            entity.remove(Acceleration::class.java)
            entity.remove(Timer::class.java)
            entity.remove(Travel::class.java)
            return
        }

        timer.millisSinceStart += deltaTime

        val hasShellTouchedGround = travel.origin.y - travel.maxTravelDistance.y >= transform.position.y
        if (hasShellTouchedGround && velocity.velocity.y < 0) {
            velocity.velocity.y *= -0.25f
            velocity.velocity.x *= 0.25f
            timer.millisSinceStart = 0f
            entity.remove(Foreground::class.java) //let the shell now be rendered in the background instead
            entity.remove(IgnoreAudio::class.java) //allow the sound effect to finally play
        } else {
            velocity.velocity.y += acceleration.accelerationFactor * timer.millisSinceStart
        }
    }
}