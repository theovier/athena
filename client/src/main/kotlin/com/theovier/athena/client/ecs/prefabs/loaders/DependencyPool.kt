package com.theovier.athena.client.ecs.prefabs.loaders

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity

data class DependencyPool(
    val entities: HashMap<String, Entity> = HashMap(),
    val components: HashMap<String, Component> = HashMap()
) {
    fun contains(key: String) = entities.contains(key) || components.contains(key)
    fun containsAll(keys: Array<String>) = keys.all { contains(it) }
    fun add(id: String, entity: Entity): Entity? = entities.put(id, entity)
    fun add(id: String, component: Component): Component? = components.put(id, component)
    fun clear() {
        entities.clear()
        components.clear()
    }
}