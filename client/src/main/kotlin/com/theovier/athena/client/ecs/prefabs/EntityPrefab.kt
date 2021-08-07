package com.theovier.athena.client.ecs.prefabs

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import mu.KotlinLogging


@Serializable
data class EntityPrefab(val name: String, val components: List<@Polymorphic Foo>)

interface Foo {
    fun doStuff()
}

@Serializable
data class Bar(val name: String) : Foo {
    override fun doStuff(){

    }
}

@Serializable
class Buzz : Foo {
    override fun doStuff() {

    }
}

private val log = KotlinLogging.logger {}
fun main() {
    //does not work
    GameObject.instantiate("player")
}