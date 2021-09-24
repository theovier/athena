package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.mapperFor

/**
 * Singleton Component
 */
class Trauma : Component, Poolable {
    var trauma = MIN_TRAUMA
        set(trauma) {
            field = MathUtils.clamp(trauma, MIN_TRAUMA, MAX_TRAUMA)
        }

    var reductionFactor = DEFAULT_REDUCTION_FACTOR
    var maxTranslationalOffset = DEFAULT_MAX_TRANSLATIONAL_OFFSET
    var maxRotationalOffset = DEFAULT_MAX_ROTATIONAL_OFFSET

    override fun reset() {
        trauma = MIN_TRAUMA
    }

    companion object {
        val MAPPER = mapperFor<Trauma>()
        const val MIN_TRAUMA = 0f
        const val MAX_TRAUMA = 1f
        const val DEFAULT_REDUCTION_FACTOR = 0.6f
        const val DEFAULT_MAX_TRANSLATIONAL_OFFSET = 0.5f
        const val DEFAULT_MAX_ROTATIONAL_OFFSET = 5f
    }
}

val Entity.trauma: Trauma
    get() = this[Trauma.MAPPER] ?: throw GdxRuntimeException("Trauma component for entity '$this' is null")