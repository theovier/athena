package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import com.talosvfx.talos.runtime.ParticleEffectDescriptor
import com.talosvfx.talos.runtime.ParticleEffectInstance
import ktx.ashley.get
import ktx.ashley.mapperFor

class TalosParticle : Component, Poolable {
    lateinit var effect: ParticleEffectInstance
    val offset = Vector2()

    override fun reset() {
        effect.restart()
    }

    companion object {
        val MAPPER = mapperFor<TalosParticle>()
    }
}

val Entity.talosParticle: TalosParticle
    get() = this[TalosParticle.MAPPER] ?: throw GdxRuntimeException("TalosParticle for entity '$this' is null")