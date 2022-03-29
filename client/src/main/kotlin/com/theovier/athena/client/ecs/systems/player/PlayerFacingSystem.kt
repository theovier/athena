package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.input
import com.theovier.athena.client.misc.spine.faceDirection
import ktx.ashley.allOf

class PlayerFacingSystem : IteratingSystem(allOf(Player::class, Transform::class, Spine::class).get())  {
    private lateinit var input: Input

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        input = engine.input
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val skeleton = entity.spine.skeleton

        //enables walking backwards and aiming in the opposite direction
        if (input.isAiming) {
            skeleton.faceDirection(input.aim)
        } else {
            skeleton.faceDirection(transform.forward)
        }
    }
}