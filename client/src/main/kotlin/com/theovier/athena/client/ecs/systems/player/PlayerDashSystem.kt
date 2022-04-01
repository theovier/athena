package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.movement.Dash
import com.theovier.athena.client.ecs.components.movement.Velocity
import com.theovier.athena.client.ecs.components.movement.dash
import com.theovier.athena.client.ecs.components.movement.velocity
import com.theovier.athena.client.ecs.extensions.InputDrivenIteratingSystem
import com.theovier.athena.client.ecs.prefabs.Prefab
import ktx.ashley.allOf
import ktx.math.times

class PlayerDashSystem : InputDrivenIteratingSystem(allOf(Player::class, Dash::class, Velocity::class, Transform::class).get()) {

    override fun processEntity(player: Entity, deltaTime: Float) {
        val transform = player.transform
        val dash = player.dash
        val velocity = player.velocity
        val direction = transform.forward

        if (input.dash && dash.timeLeft == dash.duration) {
            Prefab.instantiate("dashSound")
        }

        if (input.dash) {
            dash.timeLeft -= deltaTime
            velocity.velocity = direction * dash.speed
        }

        if (dash.timeLeft <= 0) {
            input.dash = false
            dash.timeLeft = dash.duration
        }
    }
}
