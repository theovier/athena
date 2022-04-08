package com.theovier.athena.client.ecs.prefabs.loaders

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.extensions.addChild
import com.theovier.athena.client.ecs.prefabs.loaders.components.*
import com.theovier.athena.client.ecs.prefabs.loaders.components.animation.AnimationComponentLoader
import com.theovier.athena.client.ecs.prefabs.loaders.components.animation.controllers.PlayerAnimationControllerComponentLoader
import com.theovier.athena.client.ecs.prefabs.loaders.components.animation.controllers.WiggleAnimationControllerComponentLoader
import ktx.ashley.plusAssign
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.lang.IllegalArgumentException

class PrefabLoader : EntityLoader, KoinComponent {
    private val log = KotlinLogging.logger {}
    private val mapComponentLoader by inject<MapComponentLoader>()
    private val spriteComponentLoader by inject<SpriteComponentLoader>()
    private val physicsComponentLoader by inject<PhysicsComponentLoader>()
    private val soundComponentLoader by inject<SoundComponentLoader>()
    private val spineComponentLoader by inject<SpineComponentLoader>()
    private val particleComponentLoader by inject<ParticleComponentLoader>()

    private val componentLoaders = mapOf(
        "acceleration" to AccelerationComponentLoader(),
        "aim" to AimComponentLoader(),
        "aimAssist" to AimAssistComponentLoader(),
        "animation" to AnimationComponentLoader(),
        "cameraTarget" to CameraTargetComponentLoader(),
        "critChance" to CriticalHitChanceComponentLoader(),
        "crosshair" to CrosshairComponentLoader(),
        "damage" to DamageComponentLoader(),
        "direction" to DirectionComponentLoader(),
        "dash" to DashComponentLoader(),
        "dustTrail" to DustTrailComponentLoader(),
        "fade" to FadeComponentLoader(),
        "foreground" to ForegroundComponentLoader(),
        "friction" to FrictionComponentLoader(),
        "healthBar" to HealthBarComponentLoader(),
        "health" to HealthComponentLoader(),
        "impact" to ImpactComponentLoader(),
        "invincible" to InvincibleComponentLoader(),
        "lifetime" to LifetimeComponentLoader(),
        "loot" to LootComponentLoader(),
        "map" to mapComponentLoader,
        "money" to MoneyComponentLoader(),
        "particle" to particleComponentLoader,
        "physics" to physicsComponentLoader,
        "player" to PlayerComponentLoader(),
        "playerAnimationController" to PlayerAnimationControllerComponentLoader(),
        "ignoreAudio" to IgnoreAudioComponentLoader(),
        "sound" to soundComponentLoader,
        "spin" to SpinComponentLoader(),
        "spine" to spineComponentLoader,
        "sprite" to spriteComponentLoader,
        "targetable" to TargetableComponentLoader(),
        "text" to TextComponentLoader(),
        "timer" to TimerComponentLoader(),
        "trauma" to TraumaComponentLoader(),
        "transform" to TransformComponentLoader(),
        "travel" to TravelComponentLoader(),
        "velocity" to VelocityComponentLoader(),
        "wiggle" to WiggleComponentLoader(),
        "wiggleAnimationController" to WiggleAnimationControllerComponentLoader()
    )
    private var currentPrefabPath = ""
    private val dependencyPool = DependencyPool()
    private val componentsWithMissingDependencies = HashSet<ComponentData>()

    companion object {
        const val PREFAB_PATH_PREFIX = "/prefabs"
        const val PREFAB_EXTENSION = "json"
        const val COMPONENTS = "components"
        const val CHILDREN = "children"
        const val REQUIRES_DEPENDENCIES = "dependencies"
        const val ID = "id"
    }

    override fun loadFromFile(fileName: String, configure: Entity.() -> Unit): Entity {
        currentPrefabPath = "$PREFAB_PATH_PREFIX/$fileName.$PREFAB_EXTENSION"
        val prefabStream = PrefabLoader::class.java.getResourceAsStream(currentPrefabPath)
        val jsonReader = JsonReader()
        val entityRoot = jsonReader.parse(prefabStream)
        val entity = load(entityRoot, configure)
        retryLoadingComponentsWithDependencies()
        cleanup()
        return entity
    }

    fun load(entityRoot: JsonValue, configure: Entity.() -> Unit = {}): Entity {
        val entity = Entity()
        if(!entityRoot.has(ID)) {
            throw IllegalArgumentException("An entity is missing an ID attribute in $currentPrefabPath")
        }
        val id = entityRoot.getString(ID)
        if (entityRoot.has(COMPONENTS)) {
            val componentRoot = entityRoot.get(COMPONENTS)
            val components = loadComponents(componentRoot, id)
            components.forEach { entity += it }
        }
        if (entityRoot.has(CHILDREN)) {
            val childrenRoot = entityRoot.get(CHILDREN)
            val children = loadChildren(childrenRoot)
            children.forEach { entity.addChild(it) }
        }
        configure(entity)
        dependencyPool.add(id, entity)
        return entity
    }

    private fun loadComponents(node: JsonValue, belongsTo: String): List<Component> {
        val components = arrayListOf<Component>()
        val iterator = node.iterator()
        while (iterator.hasNext()) {
            val componentRoot = iterator.next()
            val component = loadComponent(componentRoot, belongsTo)
            if (component != null) {
                components += component
            }
        }
        return components
    }

    private fun loadComponent(componentRoot: JsonValue, belongsTo: String): Component? {
        if (componentRoot.has(REQUIRES_DEPENDENCIES)) {
            val requiredDependencies = componentRoot.get(REQUIRES_DEPENDENCIES).asStringArray()
            return loadComponent(componentRoot, belongsTo, requiredDependencies)
        }
        return load(componentRoot, belongsTo)
    }

    private fun loadComponent(componentRoot: JsonValue, belongsTo: String, dependencies: Array<String>): Component? {
        if (dependencyPool.containsAll(dependencies)) {
            return load(componentRoot, belongsTo)
        } else {
            val componentName = componentRoot.name
            val id = "$belongsTo.$componentName"
            componentsWithMissingDependencies += ComponentData(componentRoot, id, belongsTo, dependencies)
            return null
        }
    }

    private fun load(componentRoot: JsonValue, entityId: String) : Component? {
        val componentName = componentRoot.name
        val componentReader = componentLoaders[componentName]
        if (componentReader != null) {
            val component = componentReader.load(componentRoot, dependencyPool)
            val id = "$entityId.$componentName"
            dependencyPool.add(id, component)
            return component
        } else {
            log.error { "Could not find a suitable ComponentReader for '$componentName' in $currentPrefabPath. Hence component has not been added to entity." }
            return null
        }
    }

    private fun loadChildren(node: JsonValue): List<Entity> {
        val children = arrayListOf<Entity>()
        val iterator = node.iterator()
        while (iterator.hasNext()) {
            val childRoot = iterator.next()
            val child = load(childRoot)
            children += child
        }
        return children
    }

    private fun retryLoadingComponentsWithDependencies() {
        val resolvedComponents = ArrayList<ComponentData>()
        var hasLoadedAnotherComponent: Boolean
        do {
            componentsWithMissingDependencies.forEach {
                val component = loadComponent(it.json, it.belongsTo, it.dependencies)
                if (component != null) {
                    resolvedComponents += it
                    attachComponentToItsEntity(component, it.belongsTo)
                }
            }
            hasLoadedAnotherComponent = componentsWithMissingDependencies.removeAll(resolvedComponents)
        } while(hasLoadedAnotherComponent)
        logUnresolvableComponents()
    }

    private fun logUnresolvableComponents() {
        componentsWithMissingDependencies.forEach {
            log.error { "Dependencies for $it were not found, thus this component could not be loaded." }
        }
    }

    private fun attachComponentToItsEntity(component: Component, ownerId: String) {
        val entity = dependencyPool.entities[ownerId]
        if (entity != null) {
            entity += component
        } else {
            log.error { "Could not find entity $ownerId to attach component to." }
        }
    }

    private fun cleanup() {
        dependencyPool.clear()
        componentsWithMissingDependencies.clear()
    }
}