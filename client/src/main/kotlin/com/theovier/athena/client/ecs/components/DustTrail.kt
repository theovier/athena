package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.mapperFor

class DustTrail : Component, Poolable {
    var spawnFrequency = DEFAULT_SPAWN_FREQUENCY_IN_SECONDS
    var maxSpawnFrequency = DEFAULT_MAXIMUM_SPAWN_FREQUENCY
    var minSpawnFrequency = DEFAULT_MINIMUM_SPAWN_FREQUENCY
    var timer = spawnFrequency

    val canSpawn: Boolean
        get() = timer <= 0f

    fun resetTimer() {
        spawnFrequency = MathUtils.random(minSpawnFrequency, maxSpawnFrequency) //todo use own randomness lib
        timer = spawnFrequency
    }

    override fun reset() {
        resetTimer()
        spawnFrequency = DEFAULT_SPAWN_FREQUENCY_IN_SECONDS
    }

    companion object {
        const val DEFAULT_SPAWN_FREQUENCY_IN_SECONDS = 1.0f
        const val DEFAULT_MAXIMUM_SPAWN_FREQUENCY = 1.5f
        const val DEFAULT_MINIMUM_SPAWN_FREQUENCY = 0.6f
        val MAPPER = mapperFor<DustTrail>()
    }
}

val Entity.dustTrail: DustTrail
    get() = this[DustTrail.MAPPER] ?: throw GdxRuntimeException("DustTrail for entity '$this' is null")