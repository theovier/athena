package com.theovier.athena.client.ecs

import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.Parent
import com.theovier.athena.client.ecs.components.hasParentComponent
import com.theovier.athena.client.ecs.components.parent

fun Entity.addChild(child: Entity) {
    val parent = Parent()
    parent.entity = this
    if (this.hasParentComponent) {
        parent.hierarchyLevel = this.parent.hierarchyLevel + 1
    }
    child.add(parent)
}

