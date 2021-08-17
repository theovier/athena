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
        var originX: Float? = null
        var originY: Float? = null

        if (decoder is XML.XmlInput) {
            val reader = decoder.input
            for (attribute in reader.attributes) {
                when(attribute.localName) {
                    "texture" -> {
                        val textureName = attribute.value
                        val texture: Texture = AthenaGame.assetStorage[textureName]
                        sprite.setRegion(texture)
                        sprite.setSize(texture.width * AthenaGame.UNIT_SCALE, texture.height * AthenaGame.UNIT_SCALE)
                        //sprite.setOrigin(sprite.width, sprite.height * 0.5f) //todo set this via attribute
                    }
                    "originX" -> originX = attribute.value.toFloat()
                    "originY" -> originY = attribute.value.toFloat()
                    else -> log.debug { "<Sprite> element does only expect arguments 'texture', 'originX' and 'originY'." }
                }
            }
            reader.next()
        }

        //center origin by default and override it when needed
        sprite.setOriginCenter()

        if (originX !== null && originY !== null) {
            sprite.setOrigin(sprite.width * originX, sprite.height * originY)
        } else if (originX !== null) {
            sprite.setOrigin(sprite.width * originX, sprite.originY)
        } else if (originY !== null) {
            sprite.setOrigin(sprite.originX, sprite.originY)
        }

        return sprite
    }
}