package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.has
import ktx.ashley.mapperFor

//used to determine if aim assist should be active
class AimAssist : Component, Poolable {
    var distance = DEFAULT_DISTANCE
    var maxAngle = DEFAULT_MAX_ANGLE
    var degreesBetweenAngleChecks = DEFAULT_DEGREES_BETWEEN_ANGLE_CHECKS
    var deadzone = DEFAULT_DEAD_ZONE

    override fun reset() {
        maxAngle = DEFAULT_MAX_ANGLE
        degreesBetweenAngleChecks = DEFAULT_DEGREES_BETWEEN_ANGLE_CHECKS
    }

    companion object {
        val MAPPER = mapperFor<AimAssist>()
        const val DEFAULT_DISTANCE = 15f
        const val DEFAULT_MAX_ANGLE = 6
        const val DEFAULT_DEGREES_BETWEEN_ANGLE_CHECKS = 3
        const val DEFAULT_DEAD_ZONE = 15f
    }
}

val Entity.aimAssist: AimAssist
    get() = this[AimAssist.MAPPER] ?: throw GdxRuntimeException("AimAssist component for entity '$this' is null")


val Entity.hasAimAssist: Boolean
    get() = this.has(AimAssist.MAPPER)