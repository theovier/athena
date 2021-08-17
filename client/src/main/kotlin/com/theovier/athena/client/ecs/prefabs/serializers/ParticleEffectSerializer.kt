package com.theovier.athena.client.ecs.prefabs.serializers

import com.badlogic.gdx.Gdx
import com.theovier.athena.client.AthenaGame
import com.theovier.athena.client.ecs.prefabs.wrappers.SerializableParticleEffect
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import mu.KotlinLogging
import nl.adaptivity.xmlutil.attributes
import nl.adaptivity.xmlutil.serialization.XML

@Serializer(forClass = SerializableParticleEffect::class)
class ParticleEffectSerializer : KSerializer<SerializableParticleEffect> {
    private val log = KotlinLogging.logger {}

    override fun deserialize(decoder: Decoder): SerializableParticleEffect {
        val effect = SerializableParticleEffect()
        var propertyFilePath = ""
        var particleSpriteFolderPath = ""

        if (decoder is XML.XmlInput) {
            val reader = decoder.input
            for (attribute in reader.attributes) {
                when(attribute.localName) {
                    "propertyFile" -> {
                        propertyFilePath = attribute.value
                    }
                    "particleSpriteFolder" -> {
                        particleSpriteFolderPath = attribute.value
                    }
                    else -> log.warn { "<ParticleEffect> element does only expect arguments 'propertyFile' and 'particleSpriteFolder'." }
                }
            }
            reader.next()
        }
        effect.load(Gdx.files.local(propertyFilePath), Gdx.files.local(particleSpriteFolderPath))
        effect.scaleEffect(AthenaGame.UNIT_SCALE)
        return effect
    }
}