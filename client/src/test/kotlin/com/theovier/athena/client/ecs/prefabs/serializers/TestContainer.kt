package com.theovier.athena.client.ecs.prefabs.serializers

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.XmlStreaming
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlElement
import java.io.FileNotFoundException
import java.io.InputStream

@Serializable
data class TestContainer(
    @XmlElement(true)
    @Serializable(with = Vector2Serializer::class)
    val vector2: Vector2 = Vector2(),

    @XmlElement(true)
    @Serializable(with = Vector3Serializer::class)
    val vector3: Vector3 = Vector3(),

) {
    companion object {
        private const val ENCODING = "UTF-8"

        fun load(name: String): TestContainer {
            val stream = Vector2SerializerTest::class.java.getResourceAsStream("/deserialization/$name.xml")
            if (stream != null) {
                return loadFromStream(stream)
            } else {
                throw FileNotFoundException("Could not load deserialization test file '$name'.xml")
            }
        }

        fun loadFromStream(stream: InputStream): TestContainer {
            val reader = XmlStreaming.newReader(stream, ENCODING)
            return XML.decodeFromReader(reader)
        }
    }
}

