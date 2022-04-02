package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Loot
import com.theovier.athena.client.ecs.components.movement.Dash
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class DashComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Dash {
        return Dash().apply {
            speed = componentJSON.getFloat("speed", Dash.DEFAULT_SPEED)
            duration = componentJSON.getFloat("duration", Dash.DEFAULT_DURATION)
            timeLeft = componentJSON.getFloat("timeLeft", Dash.DEFAULT_TIME_LEFT)
            timeBetweenDashes = componentJSON.getFloat("timeBetweenDashes", Dash.DEFAULT_TIME_BETWEEN_DASHES)
            canNextDashInSeconds = componentJSON.getFloat("canDashInSeconds", Dash.DEFAULT_CAN_DASH_IN_SECONDS)
        }
    }
}