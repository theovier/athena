package com.theovier.athena.client.ecs.prefabs

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.*
import kotlinx.serialization.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ktx.ashley.plusAssign
import mu.KotlinLogging
import nl.adaptivity.xmlutil.XmlStreaming
import nl.adaptivity.xmlutil.serialization.XML
import java.io.FileNotFoundException
import java.io.InputStream

private val log = KotlinLogging.logger {}

@Serializable
class Prefab(val components: List<@Polymorphic Component>) {

    companion object {
        private const val ENCODING = "utf-8"
        private val CACHE = HashMap<String, Entity>()
        private val MODULE = SerializersModule {
            polymorphic(Component::class) {
                subclass(CameraTarget::class)
                subclass(Lifetime::class)
                subclass(Movement::class)
                subclass(Player::class)
                subclass(SpriteRenderer::class)
                subclass(Transform::class)
            }
        }

        private fun load(name: String): Prefab {
            val prefabStream = Prefab::class.java.getResourceAsStream("/prefabs/$name.xml")
            if (prefabStream != null) {
                return loadFromStream(prefabStream)
            } else {
                log.error { "Prefab '$name' could not be found." }
                throw FileNotFoundException("Could not load prefab '$name'.xml")
            }
        }

        private fun loadFromStream(stream: InputStream): Prefab {
            val reader = XmlStreaming.newReader(stream, ENCODING)
            return XML(serializersModule = MODULE) {
                autoPolymorphic = true
            }.decodeFromReader(reader)
        }

        fun instantiate(name: String, configure: Entity.() -> Unit = {}): Entity {
            return CACHE.getOrDefault(name, instantiateFresh(name, configure))
        }

        private fun instantiateFresh(name: String, configure: Entity.() -> Unit = {}): Entity {
            val entity = Entity()
            val prefab = load(name)
            prefab.components.forEach { entity += it }
            configure(entity)
            CACHE[name] = entity
            return entity
        }
    }
}