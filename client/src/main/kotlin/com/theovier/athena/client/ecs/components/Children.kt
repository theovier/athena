package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.has
import ktx.ashley.mapperFor

/**
 * https://skypjack.github.io/2019-06-25-ecs-baf-part-4
 * Currently we just use a maximum number of children, in the future
 * - if needed - it may be suitable to implement another component
 * with "prev", "next", iterator-like structure.
 */
class Children : Component {
    var count = 0
    var children = arrayOfNulls<Entity>(MAX_CHILDREN)

    companion object {
        val MAPPER = mapperFor<Children>()
        const val MAX_CHILDREN = 5
    }
}

val Entity.children: Children
    get() = this[Children.MAPPER] ?: throw GdxRuntimeException("Children component for entity '$this' is null")

val Entity.hasChildrenComponent: Boolean
    get() = this.has(Children.MAPPER)

val Entity.hasNoChildrenComponent: Boolean
    get() = !this.hasChildrenComponent