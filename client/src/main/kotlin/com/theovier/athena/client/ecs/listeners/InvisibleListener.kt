package com.theovier.athena.client.ecs.listeners

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.theovier.athena.client.ecs.components.render.Invisible
import com.theovier.athena.client.ecs.components.children
import com.theovier.athena.client.ecs.components.hasChildrenComponent

class InvisibleListener : EntityListener {

    override fun entityAdded(entity: Entity) {
        if (entity.hasChildrenComponent) {
            hideChildren(entity.children.children)
        }
    }

    override fun entityRemoved(entity: Entity) {
        if (entity.hasChildrenComponent) {
            showChildren(entity.children.children)
        }
    }

    private fun hideChildren(children: Array<Entity?>) {
        children.forEach {
            it?.add(Invisible())
        }
    }

    private fun showChildren(children: Array<Entity?>) {
        children.forEach {
            it?.remove(Invisible::class.java)
        }
    }
}