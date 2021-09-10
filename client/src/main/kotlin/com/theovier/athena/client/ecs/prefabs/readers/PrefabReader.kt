package com.theovier.athena.client.ecs.prefabs.readers

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import ktx.ashley.plusAssign
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PrefabReader : EntityReader, KoinComponent {
    private val log = KotlinLogging.logger {}
    private val spriteComponentReader by inject<SpriteComponentReader>()

    private val componentReaders = mapOf(
        "aim" to AimComponentReader(),
        "cameraTarget" to CameraTargetComponentReader(),
        "crosshair" to CrosshairComponentReader(),
        "lifetime" to LifetimeComponentReader(),
        "movement" to MovementComponentReader(),
        "particle" to ParticleComponentReader(),
        "player" to PlayerComponentReader(),
        "spine" to SpineAnimationComponentReader(),
        "sprite" to spriteComponentReader,
        "map" to TiledMapComponentReader(),
        "transform" to TransformComponentReader(),
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