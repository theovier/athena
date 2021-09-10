package com.theovier.athena.client.ecs.prefabs.loaders

import com.badlogic.ashley.core.Entity

class PrefabDemo {
    companion object {
        private val cache = HashMap<String, Entity>()
        private val reader: EntityLoader = PrefabLoader()

        fun instantiate(name: String, configure: Entity.() -> Unit = {}): Entity {
            return cache.getOrDefault(name, instantiateFresh(name, configure))
        }

        private fun instantiateFresh(name: String, configure: Entity.() -> Unit = {}): Entity {
            return reader.load(name, configure)
        }
    }
}