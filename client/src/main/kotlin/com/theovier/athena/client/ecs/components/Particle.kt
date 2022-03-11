package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import com.talosvfx.talos.runtime.ParticleEffectDescriptor
import ktx.ashley.get
import ktx.ashley.mapperFor

class Particle : Component, Poolable {
    var effect = ParticleEffect() //todo pool
    val offset = Vector2()

    //var descriptor = ParticleEffectDescriptor() //todo: dont need that here. only in particle loader


    override fun reset() {
        effect.reset()
    }

    companion object {
        val MAPPER = mapperFor<Particle>()
    }
}

val Entity.particle: Particle
    get() = this[Particle.MAPPER] ?: throw GdxRuntimeException("Particle for entity '$this' is null")