package com.theovier.athena.client.ecs

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.*
import kotlin.reflect.KClass

fun Entity.addChild(child: Entity): Boolean {
    if (this.hasNoChildrenComponent) {
        this.add(Children())
    }
    val childComponent = this.children
    if (childComponent.count < Children.MAX_CHILDREN) {
        childComponent.children[childComponent.count] = child
        childComponent.count++
    } else {
        return false
    }
    this.addParentComponentToChild(child)
    return true
}

private fun Entity.addParentComponentToChild(child: Entity) {
    val parent = Parent()
    parent.entity = this
    if (this.hasParentComponent) {
        parent.hierarchyLevel = this.parent.hierarchyLevel + 1
    }
    child.add(parent)
}

fun <T: Component> Entity.remove(componentClass: KClass<T>): T {
    return remove(componentClass.java)
}