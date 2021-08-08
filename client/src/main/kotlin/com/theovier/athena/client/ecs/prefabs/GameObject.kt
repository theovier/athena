package com.theovier.athena.client.ecs.prefabs

import com.badlogic.ashley.core.Component
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import mu.KotlinLogging
import nl.adaptivity.xmlutil.XmlStreaming
import nl.adaptivity.xmlutil.serialization.XML
import java.lang.RuntimeException

private val log = KotlinLogging.logger {}

class GameObject {
    companion object {

        val baseModule = SerializersModule {
            polymorphic(Component::class) {
                subclass(Foo::class)
                subclass(Bar::class)
                //subclass(Buzz::class)
            }
        }


        fun instantiate(name: String): Prefab {
            val prefabStream = GameObject::class.java.getResourceAsStream("/prefabs/$name.xml")
            if (prefabStream != null) {
                val reader = XmlStreaming.newReader(prefabStream, "utf-8")
                val prefab = XML(serializersModule = baseModule){
                    autoPolymorphic = false
                }.decodeFromReader<Prefab>(reader)
                return prefab
            } else {
                log.error { "Prefab '$name' could not be found." }
                throw RuntimeException("Could not load prefab '$name'.xml")
            }
        }
    }



}