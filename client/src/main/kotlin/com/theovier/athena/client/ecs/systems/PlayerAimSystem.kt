package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.Aim
import com.theovier.athena.client.ecs.components.Player
import com.theovier.athena.client.ecs.components.aim
import com.theovier.athena.client.ecs.components.transform
import com.theovier.athena.client.math.xy
import ktx.ashley.allOf
import ktx.math.minus

class PlayerAimSystem(private val screenCoordinatesToWorldCoordinates: (Vector2) -> Vector2)
    : IteratingSystem(allOf(Player::class, Aim::class).get())  {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val playerPosition = entity.transform.position.xy
        val mousePosition = Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
        val mousePositionInWorld = screenCoordinatesToWorldCoordinates(mousePosition)
        val direction = (mousePositionInWorld - playerPosition).nor()
        entity.aim.direction = direction

        val distanceToMousePosition = (mousePositionInWorld - playerPosition).len()

        entity.aim.distanceToAimTarget = distanceToMousePosition
    }
}