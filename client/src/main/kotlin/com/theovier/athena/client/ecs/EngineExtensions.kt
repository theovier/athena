package com.theovier.athena.client.ecs

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.Input
import com.theovier.athena.client.ecs.components.children
import com.theovier.athena.client.ecs.components.hasChildrenComponent
import ktx.ashley.allOf
import ktx.ashley.get

//ugly hack as we cannot add new fields to the Engine class
val Engine.input: Input
    get() = this.getEntitiesFor(
        allOf(Input::class).get()
    )
        .first()[Input.MAPPER]!!

fun Engine.removeEntityAndAllChildren(entity: Entity) {
    if (entity.hasChildrenComponent) {
        entity.children.children.forEach { child ->
            if (child != null) {
                this.removeEntityAndAllChildren(child)
            }
        }
    }
    this.removeEntity(entity)
}

fun Engine.addEntityAndAllChildren(entity: Entity) {
    if (entity.hasChildrenComponent) {
        entity.children.children.forEach { child ->
            if (child != null) {
                this.addEntityAndAllChildren(child)
            }
        }
    }
    this.addEntity(entity)
}