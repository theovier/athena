package com.theovier.athena.client.ecs.prefabs

import com.badlogic.ashley.core.Component
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import mu.KotlinLogging
import nl.adaptivity.xmlutil.serialization.XmlPolyChildren
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
data class Prefab(@XmlPolyChildren(arrayOf(".Foo", ".Bar")) val components: List<@Polymorphic Component>)

@Serializable
class Foo(val name: String) : Component

@Serializable
class Bar(val id: Int) : Component


private val log = KotlinLogging.logger {}
fun main() {
    val prefab = GameObject.instantiate("player")

    prefab.components.forEach {
        when(it) {
            is Foo -> {
                log.info { it.name }
            }
            is Bar -> {
                log.info { it.id }
            }
        }
    }

}