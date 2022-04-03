package com.theovier.athena.client.ecs.components.render

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import com.talosvfx.talos.runtime.ParticleEffectInstance
import ktx.ashley.get
import ktx.ashley.mapperFor

class Particle : Component, Poolable {
    lateinit var effect: ParticleEffectInstance
    val offset = Vector2()

    override fun reset() {
        effect.restart()
    }

    companion object {
        val MAPPER = mapperFor<Particle>()
    }
}

val Entity.particle: Particle
    get() = this[Particle.MAPPER] ?: throw GdxRuntimeException("Particle for entity '$this' is null")