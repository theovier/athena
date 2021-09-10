package com.theovier.athena.client.ecs.prefabs.readers

import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.lifetime
import com.theovier.athena.client.ecs.components.transform
import mu.KotlinLogging

class PrefabDemo {
    companion object {
        private val cache = HashMap<String, Entity>()
        private val reader: EntityReader = PrefabReader()

        fun instantiate(name: String, configure: Entity.() -> Unit = {}): Entity {
            return cache.getOrDefault(name, instantiateFresh(name, configure))
        }

        private fun instantiateFresh(name: String, configure: Entity.() -> Unit = {}): Entity {
            return reader.read(name, configure)
        }
    }
}