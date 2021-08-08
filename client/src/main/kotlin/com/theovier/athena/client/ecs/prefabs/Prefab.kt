package com.theovier.athena.client.ecs.prefabs

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector3
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.prefabs.serializers.Vector3Serializer
import kotlinx.serialization.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ktx.ashley.get
import ktx.ashley.plusAssign
import mu.KotlinLogging
import nl.adaptivity.xmlutil.XmlStreaming
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlPolyChildren
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.io.InputStream
import java.lang.RuntimeException

@Serializable
class Prefab(val components: List<@Polymorphic Component>) {

    companion object {
        private const val ENCODING = "utf-8"

        private val serializerModule = SerializersModule {
            polymorphic(Component::class) {
                subclass(CameraTarget::class)
                subclass(Movement::class)
                subclass(Player::class)
                subclass(Transform::class)
            }
        }

        private fun load(name: String): Prefab {
            val prefabStream = Prefab::class.java.getResourceAsStream("/prefabs/$name.xml")
            if (prefabStream != null) {
                return loadFromStream(prefabStream)
            } else {
                log.error { "Prefab '$name' could not be found." }
                throw RuntimeException("Could not load prefab '$name'.xml")
            }
        }

        private fun loadFromStream(stream: InputStream): Prefab {
            val reader = XmlStreaming.newReader(stream, ENCODING)
            return XML(serializersModule = serializerModule) {
                autoPolymorphic = true
            }.decodeFromReader(reader)
        }

        fun instantiate(name: String): Entity {
            val entity = Entity()
            val prefab = load(name)
            prefab.components.forEach { entity += it }
            return entity
        }
    }
}

private val log = KotlinLogging.logger {}
fun main() {
    val entityFromPrefab = Prefab.instantiate("player")
    log.debug { entityFromPrefab.transform.position }
    log.debug { entityFromPrefab.get<Movement>()?.maxSpeed }
}