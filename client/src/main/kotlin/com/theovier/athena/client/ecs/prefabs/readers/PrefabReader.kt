package com.theovier.athena.client.ecs.prefabs.readers

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import ktx.ashley.plusAssign
import mu.KotlinLogging

class PrefabReader : EntityReader {
    private val log = KotlinLogging.logger {}
    private val componentReaders = mapOf(
        "lifetime" to LifetimeComponentReader(),
        "transform" to TransformComponentReader(),
        "particle" to ParticleComponentReader(),
        "aim" to AimComponentReader()
    )

    override fun read(fileName: String, configure: Entity.() -> Unit): Entity {
        val entity = Entity()
        val components = readComponentsFromJSONFile(fileName)
        components.forEach { entity += it }
        configure(entity)
        return entity
    }

    private fun readComponentsFromJSONFile(fileName: String): List<Component> {
        val prefabPath = "/prefabs/$fileName.json"
        val prefabStream = PrefabReader::class.java.getResourceAsStream(prefabPath)
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