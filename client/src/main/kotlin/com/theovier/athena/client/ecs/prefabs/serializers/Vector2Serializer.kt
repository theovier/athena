package com.theovier.athena.client.ecs.prefabs.serializers

import com.badlogic.gdx.math.Vector2
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import nl.adaptivity.xmlutil.EventType
import nl.adaptivity.xmlutil.XmlException
import nl.adaptivity.xmlutil.attributes
import nl.adaptivity.xmlutil.serialization.XML

@Serializer(forClass = Vector2::class)
class Vector2Serializer : KSerializer<Vector2> {

    override fun deserialize(decoder: Decoder): Vector2 {
        val vector = Vector2()

        if (decoder is XML.XmlInput) {
            val reader = decoder.input
            for (attribute in reader.attributes) {
                when(attribute.localName) {
                    "x" -> vector.x = attribute.value.toFloat()
                    "y" -> vector.y = attribute.value.toFloat()
                }
            }
            while (reader.next() != EventType.END_ELEMENT) {
                throw XmlException("Vector2 may only have attributes x,y which are all floating point numbers.")
            }
        }
        return vector
    }
}