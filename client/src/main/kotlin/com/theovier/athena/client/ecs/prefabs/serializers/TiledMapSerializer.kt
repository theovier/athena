package com.theovier.athena.client.ecs.prefabs.serializers

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.theovier.athena.client.AthenaGame
import com.theovier.athena.client.ecs.components.TiledMap
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import mu.KotlinLogging
import nl.adaptivity.xmlutil.attributes
import nl.adaptivity.xmlutil.serialization.XML

@Serializer(forClass = TiledMap::class)
class TiledMapSerializer : KSerializer<TiledMap> {
    private val log = KotlinLogging.logger {}

    override fun deserialize(decoder: Decoder): TiledMap {
        val component = TiledMap()

        if (decoder is XML.XmlInput) {
            val reader = decoder.input
            for (attribute in reader.attributes) {
                when(attribute.localName) {
                    "path" -> {
                        val mapPath = attribute.value
                        component.map = TmxMapLoader().load(mapPath)
                    }
                    else -> log.debug { "<Map> element does only expect argument 'path'." }
                }
            }
            reader.next()
        }
        return component
    }

}