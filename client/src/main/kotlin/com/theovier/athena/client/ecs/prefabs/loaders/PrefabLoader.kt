package com.theovier.athena.client.ecs.prefabs.loaders

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.prefabs.loaders.components.*
import ktx.ashley.plusAssign
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PrefabLoader : EntityLoader, KoinComponent {
    private val log = KotlinLogging.logger {}
    private val mapComponentLoader by inject<MapComponentLoader>()
    private val spriteComponentLoader by inject<SpriteComponentLoader>()
    private val physicsComponentLoader by inject<PhysicsComponentLoader>()

    private val componentLoaders = mapOf(
        "aim" to AimComponentLoader(),
        "cameraTarget" to CameraTargetComponentLoader(),
        "crosshair" to CrosshairComponentLoader(),
        "lifetime" to LifetimeComponentLoader(),
        "map" to mapComponentLoader,
        "movement" to MovementComponentLoader(),
        "particle" to ParticleComponentLoader(),
        "physics" to physicsComponentLoader,
        "player" to PlayerComponentLoader(),
        "spine" to SpineAnimationComponentLoader(),
        "sprite" to spriteComponentLoader,
        "transform" to TransformComponentLoader(),
    )

    override fun load(fileName: String, configure: Entity.() -> Unit): Entity {
        val entity = Entity()
        val components = loadComponentsFromJSONFile(fileName)
        components.forEach { entity += it }
        configure(entity)
        return entity
    }

    private fun loadComponentsFromJSONFile(fileName: String): List<Component> {
        val prefabPath = "/prefabs/$fileName.json"
        val prefabStream = PrefabLoader::class.java.getResourceAsStream(prefabPath)
        val jsonReader = JsonReader()
        val documentRoot = jsonReader.parse(prefabStream)
        return loadComponents(documentRoot, prefabPath)
    }

    private fun loadComponents(node: JsonValue, filePath: String): List<Component> {
        val components = arrayListOf<Component>()
        val iterator = node.iterator()
        while (iterator.hasNext()) {
            val componentRoot = iterator.next()
            val componentReader = componentLoaders[componentRoot.name]
            if (componentReader != null) {
                val component = componentReader.load(componentRoot)
                components += component
            } else {
                log.error { "Could not find a suitable ComponentReader for '${componentRoot.name}' in $filePath. Hence component has not been added to entity." }
            }
        }
        return components
    }
}