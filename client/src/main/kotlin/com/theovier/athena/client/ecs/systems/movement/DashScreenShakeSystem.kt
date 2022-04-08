package com.theovier.athena.client.ecs.systems.movement

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.movement.Dash
import com.theovier.athena.client.ecs.components.movement.dash
import ktx.ashley.allOf

class DashScreenShakeSystem : IteratingSystem(allOf(Dash::class, Trauma::class).get()) {

    override fun processEntity(player: Entity, deltaTime: Float) {
        val dash = player.dash
        val trauma = player.trauma

        if (dash.isCurrentlyDashing) {
            trauma.trauma += dash.trauma
        }
    }

}
