package com.theovier.athena.client.ecs.prefabs

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.theovier.athena.client.ecs.components.Transform
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ktx.ashley.addComponent
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.ashley.plusAssign
import mu.KotlinLogging
import nl.adaptivity.xmlutil.serialization.XmlPolyChildren
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.lang.RuntimeException

@Serializable
data class Prefab(@XmlPolyChildren(arrayOf(".Foo", ".Bar")) val components: List<@Polymorphic Component>)

@Serializable
class Foo(var name: String) : Component

@Serializable
class Bar(var id: Int) : Component



private val log = KotlinLogging.logger {}
fun main() {
    val engine = PooledEngine()
    val entity = Entity()

    val prefab = GameObject.instantiate("player")
    prefab.components.forEach {
        val component = when(it) {
            is Foo -> {
                Foo(it.name)
            }
            is Bar -> {
                Bar(it.id)
            }
            else -> {
                log.error { "A component in this prefab was not recognized and therefore not added." }
                return
            }
        }
        entity += component
    }
    engine.addEntity(entity)

    log.info { entity.get<Foo>()?.name }
}