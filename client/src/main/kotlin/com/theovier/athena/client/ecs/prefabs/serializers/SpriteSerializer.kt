package com.theovier.athena.client.ecs.prefabs.serializers

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.theovier.athena.client.AthenaGame
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import mu.KotlinLogging
import nl.adaptivity.xmlutil.attributes
import nl.adaptivity.xmlutil.serialization.XML

@Serializer(forClass = Sprite::class)
class SpriteSerializer : KSerializer<Sprite> {
    private val log = KotlinLogging.logger {}

    override fun deserialize(decoder: Decoder): Sprite {
        val sprite = Sprite()

        if (decoder is XML.XmlInput) {
            val reader = decoder.input
            for (attribute in reader.attributes) {
                when(attribute.localName) {
                    "texture" -> {
                        val textureName = attribute.value
                        val texture: Texture = AthenaGame.assetStorage[textureName]
                        sprite.setRegion(texture)
                        sprite.setSize(texture.width * AthenaGame.UNIT_SCALE, texture.height * AthenaGame.UNIT_SCALE)
                        sprite.setOrigin(sprite.width * 0.5f, sprite.height * 0.5f)
                    }
                    else -> log.debug { "<Sprite> element does only expect argument 'texture'." }
                }
            }
            reader.next()
        }
        return sprite
    }
}