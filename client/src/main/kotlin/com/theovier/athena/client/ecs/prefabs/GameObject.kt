package com.theovier.athena.client.ecs.prefabs

import mu.KotlinLogging
import nl.adaptivity.xmlutil.XmlStreaming
import nl.adaptivity.xmlutil.serialization.XML

private val log = KotlinLogging.logger {}

class GameObject {
    companion object {
        fun instantiate(name: String) {
            val prefabStream = GameObject::class.java.getResourceAsStream("/prefabs/$name.xml")
            if (prefabStream != null) {
                val reader = XmlStreaming.newReader(prefabStream, "utf-8")
                val prefab = XML.defaultInstance.decodeFromReader<EntityPrefab>(reader)
                log.debug { prefab }
            } else {
                log.error { "Prefab '$name' could not be found." }
            }
        }
    }
}