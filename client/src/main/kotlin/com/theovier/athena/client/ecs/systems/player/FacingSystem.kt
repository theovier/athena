package com.theovier.athena.client.ecs.systems.player

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.misc.spine.faceDirection
import ktx.ashley.allOf

class FacingSystem : IteratingSystem(allOf(Transform::class, Spine::class).get())  {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val skeleton = entity.spine.skeleton
        skeleton.faceDirection(transform.forward)
    }
}