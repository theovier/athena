package com.theovier.athena.client.ecs.prefabs

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector3
import com.theovier.athena.client.ecs.prefabs.serializers.Vector3Serializer
import kotlinx.serialization.*
import ktx.ashley.get
import mu.KotlinLogging
import nl.adaptivity.xmlutil.XmlStreaming
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlPolyChildren

@Serializable
class Prefab(
    //todo this needs to be stated here as annotation argument must be a compile time constant
    @XmlPolyChildren([".Foo", ".Bar", ".CustomComponent"])
    val components: List<@Polymorphic Component>
)

@Serializable
class Foo(var name: String) : Component

@Serializable
class Bar(var id: Int) : Component

@Serializable
data class CustomComponent(
    @XmlElement(true)
    @Serializable(with = Vector3Serializer::class)
    val vector: Vector3
) : Component


private val log = KotlinLogging.logger {}
fun main() {
    val entityFromPrefab = GameObject.instantiate("player")
    log.debug { entityFromPrefab.get<CustomComponent>()?.vector }
}