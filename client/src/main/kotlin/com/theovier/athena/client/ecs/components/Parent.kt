package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.has
import ktx.ashley.mapperFor

class Parent : Component {
    var entity: Entity? = null
    var hierarchyLevel = 0 //how deeply the corresponding entity is nested in the hierarchy

    companion object {
        val MAPPER = mapperFor<Parent>()
    }
}

val Entity.parent: Parent
    get() = this[Parent.MAPPER] ?: throw GdxRuntimeException("Parent component for entity '$this' is null")

val Entity.hasParentComponent: Boolean
    get() = this.has(Parent.MAPPER)