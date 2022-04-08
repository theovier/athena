package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
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

        if (input.dash && dash.canDash) {
            startDashing(dash)
        }
        if (dash.isCurrentlyDashing) {
            keepDashing(dash, velocity, direction, deltaTime)
        } else if (dash.canNextDashInSeconds > 0) {
            dash.canNextDashInSeconds -= deltaTime
        }
    }

    private fun startDashing(dash: Dash) {
        dash.isCurrentlyDashing = true
        dash.canNextDashInSeconds = dash.timeBetweenDashes
        dash.prefabToSpawn?.let { Prefab.instantiate(it) }
        input.dash = false //prevents that keeping the button pressed results in consecutive dashes
    }

    private fun keepDashing(dash: Dash, velocity: Velocity, direction: Vector2, deltaTime: Float) {
        dash.timeLeft -= deltaTime
        velocity.velocity = direction * dash.speed

        if (dash.finishedDashing) {
            stopDashing(dash)
        }
    }

    private fun stopDashing(dash: Dash) {
        dash.isCurrentlyDashing = false
        dash.timeLeft = dash.duration
    }
}
