package com.theovier.athena.client.ecs.prefabs.serializers

import com.badlogic.gdx.math.Vector3
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import nl.adaptivity.xmlutil.EventType
import nl.adaptivity.xmlutil.XmlException
import nl.adaptivity.xmlutil.attributes
import nl.adaptivity.xmlutil.serialization.XML

@Serializer(forClass = Vector3::class)
class Vector3Serializer : KSerializer<Vector3> {

    override fun deserialize(decoder: Decoder): Vector3 {
        val vector = Vector3()

        if (decoder is XML.XmlInput) {
            val reader = decoder.input
            for (attribute in reader.attributes) {
                when(attribute.localName) {
                    "x" -> vector.x = attribute.value.toFloat()
                    "y" -> vector.y = attribute.value.toFloat()
                    "z" -> vector.z = attribute.value.toFloat()
                }
            }
            while (reader.next() != EventType.END_ELEMENT) {
                throw XmlException("Custom element may only have attributes x,y,z.")
            }
        }
        return vector
    }
}