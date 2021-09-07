package com.theovier.athena.client.ecs.prefabs.readers

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.lifetime
import com.theovier.athena.client.ecs.components.transform
import ktx.ashley.plusAssign
import mu.KotlinLogging

private val log = KotlinLogging.logger {}
class PrefabDemo {
    companion object {
        private val cache = HashMap<String, Entity>() //todo check if this always return the exact SAME instance -> problematic
        private val componentReaders = mapOf(
            "lifetime" to LifetimeComponentReader(),
            "transform" to TransformComponentReader(),
            "particle" to ParticleComponentReader()
        )

        fun instantiate(name: String, configure: Entity.() -> Unit = {}): Entity {
            return cache.getOrDefault(name, instantiateFresh(name, configure))
        }

        private fun instantiateFresh(name: String, configure: Entity.() -> Unit = {}): Entity {
            val entity = Entity()
            val components = readComponentsFromJSONFile(name)
            components.forEach { entity += it }
            configure(entity)
            cache[name] = entity
            return entity
        }

        private fun readComponentsFromJSONFile(fileName: String): List<Component> {
            val prefabPath = "/prefabs/$fileName.json"
            val prefabStream = PrefabDemo::class.java.getResourceAsStream(prefabPath)
            val jsonReader = JsonReader()
            val documentRoot = jsonReader.parse(prefabStream)
            return readComponents(documentRoot, prefabPath)
        }

        private fun readComponents(node: JsonValue, filePath: String): List<Component> {
            val components = arrayListOf<Component>()
            val iterator = node.iterator()
            while (iterator.hasNext()) {
                val componentRoot = iterator.next()
                val componentReader = componentReaders[componentRoot.name]
                if (componentReader != null) {
                    val component = componentReader.read(componentRoot)
                    components += component
                } else {
                    log.error { "Could not find a suitable ComponentReader for '${componentRoot.name}' in $filePath. Hence component has not been added to entity." }
                }
            }
            return components
        }
    }
}

fun main() {
    val bullet = PrefabDemo.instantiate("bullet")
    log.debug { bullet.lifetime.duration }
    log.debug { bullet.transform.size }
}