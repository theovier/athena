package com.theovier.athena.client.ecs.prefabs

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import mu.KotlinLogging

@Serializable
@SerialName("entity")
data class EntityPrefab(val name: String, val components: List<Bar> = listOf())
//note: List<@Polymorphic Foo> does not work

interface Foo

@Serializable
@SerialName("bar")
data class Bar(val name: String) : Foo

@Serializable
class Buzz : Foo

private val log = KotlinLogging.logger {}
fun main() {
    //does not work
    GameObject.instantiate("player")
}