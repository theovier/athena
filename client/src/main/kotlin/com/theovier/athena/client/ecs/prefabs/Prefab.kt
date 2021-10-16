package com.theovier.athena.client.ecs.prefabs

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.theovier.athena.client.ecs.addEntityAndAllChildren
import com.theovier.athena.client.ecs.prefabs.loaders.EntityLoader
import com.theovier.athena.client.ecs.prefabs.loaders.PrefabLoader
import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent.inject

class Prefab: KoinComponent {
    companion object {
        private val cache = HashMap<String, Entity>()
        private val loader: EntityLoader = PrefabLoader()
        private val engine: PooledEngine by inject(PooledEngine::class.java)

        fun instantiate(name: String, configure: Entity.() -> Unit = {}): Entity {
            val entity = cache.getOrDefault(name, instantiateFresh(name, configure))
            engine.addEntityAndAllChildren(entity)
            return entity
        }

        private fun instantiateFresh(name: String, configure: Entity.() -> Unit = {}): Entity {
            return loader.loadFromFile(name, configure)
        }
    }
}