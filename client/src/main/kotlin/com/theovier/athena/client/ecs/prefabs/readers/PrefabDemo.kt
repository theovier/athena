package com.theovier.athena.client.ecs.prefabs.readers

import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.lifetime
import com.theovier.athena.client.ecs.components.transform
import com.theovier.athena.client.ecs.prefabs.readers.koin.*
import ktx.assets.async.AssetStorage
import mu.KotlinLogging
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val log = KotlinLogging.logger {}
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

fun main() {
    val module = module {
        single { AssetStorage() }
    }

    startKoin {
        printLogger()
        modules(module)
    }


    val bullet = PrefabDemo.instantiate("bullet")
    log.debug { bullet.lifetime.duration }
    log.debug { bullet.transform.size }
}