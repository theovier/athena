package com.theovier.athena.client.ecs.prefabs

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ktx.ashley.plusAssign
import mu.KotlinLogging
import nl.adaptivity.xmlutil.XmlStreaming
import nl.adaptivity.xmlutil.serialization.XML
import java.lang.RuntimeException

private val log = KotlinLogging.logger {}

class GameObject {
    companion object {

        val components = arrayOf(
            ".Foo",
            ".Bar",
            ".CustomComponent"
        )

        //todo get them from an array or through reflection
        private val serializerModule = SerializersModule {
            polymorphic(Component::class) {
                subclass(Foo::class)
                subclass(Bar::class)
                subclass(CustomComponent::class)
            }
        }

        private fun loadPrefab(name: String): Prefab {
            val prefabStream = GameObject::class.java.getResourceAsStream("/prefabs/$name.xml")
            if (prefabStream != null) {
                val reader = XmlStreaming.newReader(prefabStream, "utf-8")
                val prefab = XML(serializersModule = serializerModule){
                    autoPolymorphic = false
                }.decodeFromReader<Prefab>(reader)
                return prefab
            } else {
                log.error { "Prefab '$name' could not be found." }
                throw RuntimeException("Could not load prefab '$name'.xml")
            }
        }

        fun instantiate(name: String): Entity {
            val entity = Entity()
            val prefab = loadPrefab(name)
            prefab.components.forEach { entity += it }
            return entity
        }
    }
}