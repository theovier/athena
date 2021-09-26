package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.Aim
import com.theovier.athena.client.ecs.components.Spine
import com.theovier.athena.client.ecs.components.aim
import com.theovier.athena.client.ecs.components.spine
import com.theovier.athena.client.misc.spine.isMirrored
import ktx.ashley.allOf

class WeaponRotationSystem : IteratingSystem(allOf(Aim::class, Spine::class).get())  {

    override fun processEntity(wielder: Entity, deltaTime: Float) {
        val skeleton = wielder.spine.skeleton
        val weaponBone = wielder.spine.weaponBone
        val aim = wielder.aim

        if (aim.isNotCurrentlyAiming) {
            //do not change weapon rotation if we are not aiming
            return
        }

        var direction = aim.direction
        if (skeleton.isMirrored()) {
            direction = Vector2(-direction.x, direction.y)
        }
        val rotation = MathUtils.atan2(direction.y, direction.x) * MathUtils.radiansToDegrees
        weaponBone.rotation = rotation
    }
}