package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import com.theovier.athena.client.ecs.prefabs.wrappers.SerializableParticleEffect
import com.theovier.athena.client.ecs.prefabs.serializers.ParticleEffectSerializer
import com.theovier.athena.client.ecs.prefabs.serializers.Vector2Serializer
import kotlinx.serialization.Serializable
import ktx.ashley.get
import ktx.ashley.mapperFor
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
class Particle : Component, Poolable {

    @XmlElement(true)
    @XmlSerialName("Effect", "", "")
    @Serializable(with = ParticleEffectSerializer::class)
    val effect = SerializableParticleEffect() //todo pool

    @XmlElement(true)
    @XmlSerialName("Offset", "", "")
    @Serializable(with = Vector2Serializer::class)
    val offset = Vector2()

    override fun reset() {
        effect.reset()
    }

    companion object {
        val MAPPER = mapperFor<Particle>()
    }
}

val Entity.particle: Particle
    get() = this[Particle.MAPPER] ?: throw GdxRuntimeException("Particle for entity '$this' is null")