package com.theovier.athena.client.ecs.prefabs

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector3
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.prefabs.serializers.Vector3Serializer
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import ktx.ashley.addComponent
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.ashley.plusAssign
import mu.KotlinLogging
import nl.adaptivity.xmlutil.EventType
import nl.adaptivity.xmlutil.XmlException
import nl.adaptivity.xmlutil.XmlStreaming
import nl.adaptivity.xmlutil.attributes
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlPolyChildren
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.lang.RuntimeException

@Serializable
class Prefab(@XmlPolyChildren(arrayOf(".Foo", ".Bar", ".Buzz")) val components: List<@Polymorphic Component>)

@Serializable
class Foo(var name: String) : Component

@Serializable
class Bar(var id: Int) : Component

@Serializable
data class CustomContainer(
    @XmlElement(true)
    @Serializable(with = Vector3Serializer::class)
    val vector: Vector3
)




private val log = KotlinLogging.logger {}
fun main() {



    val prefabStream = GameObject::class.java.getResourceAsStream("/prefabs/custom.xml")
    if (prefabStream != null) {
        val reader = XmlStreaming.newReader(prefabStream, "utf-8")
        val customContainer = XML.decodeFromReader<CustomContainer>(reader)
        log.debug { customContainer.vector }
    }













//    val engine = PooledEngine()
//    val entity = Entity()
//
//    val prefab = GameObject.instantiate("player")
//    prefab.components.forEach {
//        val component = when(it) {
//            is Foo -> Foo(it.name)
//            is Bar -> Bar(it.id)
//            is Buzz -> Buzz()
//            else -> {
//                log.error { "A component in this prefab was not recognized and therefore not added." }
//                return
//            }
//        }
//        entity += component
//    }
//    engine.addEntity(entity)
//
//    log.info { entity.get<Foo>()?.name }
//    log.info { entity.get<Buzz>()?.vector }
}