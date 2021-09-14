package com.theovier.athena.client.ecs.prefabs

import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.prefabs.loaders.EntityLoader
import com.theovier.athena.client.ecs.prefabs.loaders.PrefabLoader

class Prefab {
    companion object {
        private val cache = HashMap<String, Entity>()
        private val loader: EntityLoader = PrefabLoader()

        fun instantiate(name: String, configure: Entity.() -> Unit = {}): Entity {
            return cache.getOrDefault(name, instantiateFresh(name, configure))
        }

        private fun instantiateFresh(name: String, configure: Entity.() -> Unit = {}): Entity {
            return loader.load(name, configure)
        }
    }
}