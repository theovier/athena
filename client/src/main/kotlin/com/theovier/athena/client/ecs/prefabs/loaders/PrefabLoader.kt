package com.theovier.athena.client.ecs.prefabs.loaders

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.addChild
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
    private val soundComponentLoader by inject<SoundComponentLoader>()
    private val spineComponentLoader by inject<SpineComponentLoader>()

    private val componentLoaders = mapOf(
        "acceleration" to AccelerationComponentLoader(),
        "aim" to AimComponentLoader(),
        "cameraTarget" to CameraTargetComponentLoader(),
        "crosshair" to CrosshairComponentLoader(),
        "damage" to DamageComponentLoader(),
        "direction" to DirectionComponentLoader(),
        "foreground" to ForegroundComponentLoader(),
        "friction" to FrictionComponentLoader(),
        "healthBar" to HealthBarComponentLoader(),
        "health" to HealthComponentLoader(),
        "invincible" to InvincibleComponentLoader(),
        "lifetime" to LifetimeComponentLoader(),
        "map" to mapComponentLoader,
        "particle" to ParticleComponentLoader(),
        "physics" to physicsComponentLoader,
        "player" to PlayerComponentLoader(),
        "ignoreAudio" to IgnoreAudioComponentLoader(),
        "sound" to soundComponentLoader,
        "spin" to SpinComponentLoader(),
        "spine" to spineComponentLoader,
        "sprite" to spriteComponentLoader,
        "text" to TextComponentLoader(),
        "timer" to TimerComponentLoader(),
        "trauma" to TraumaComponentLoader(),
        "transform" to TransformComponentLoader(),
        "travel" to TravelComponentLoader(),
        "velocity" to VelocityComponentLoader()
    )

    companion object {
        const val PREFAB_PATH_PREFIX = "/prefabs"
        const val PREFAB_EXTENSION = "json"
        const val COMPONENTS = "components"
        const val CHILDREN = "children"
    }

    override fun loadFromFile(fileName: String, configure: Entity.() -> Unit): Entity {
        val prefabPath = "$PREFAB_PATH_PREFIX/$fileName.$PREFAB_EXTENSION"
        val prefabStream = PrefabLoader::class.java.getResourceAsStream(prefabPath)
        val jsonReader = JsonReader()
        val entityRoot = jsonReader.parse(prefabStream)
        return load(entityRoot, prefabPath, configure)
    }

    override fun load(entityRoot: JsonValue, filePath: String, configure: Entity.() -> Unit): Entity {
        val entity = Entity()
        if (entityRoot.has(COMPONENTS)) {
            val componentRoot = entityRoot.get(COMPONENTS)
            val components = loadComponents(componentRoot, filePath)
            components.forEach { entity += it }
        }
        if (entityRoot.has(CHILDREN)) {
            val childrenRoot = entityRoot.get(CHILDREN)
            val children = loadChildren(childrenRoot, filePath)
            children.forEach { entity.addChild(it) }
        }
        configure(entity)
        return entity
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
    
    private fun loadChildren(node: JsonValue, filePath: String): List<Entity> {
        val children = arrayListOf<Entity>()
        val iterator = node.iterator()
        while (iterator.hasNext()) {
            val childRoot = iterator.next()
            val child = loadChild(childRoot, filePath)
            children += child
        }
        return children
    }

    private fun loadChild(entityRoot: JsonValue, filePath: String): Entity {
        return load(entityRoot, filePath)
    }
}