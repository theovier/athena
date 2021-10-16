package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.theovier.athena.client.ecs.components.Parent
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.parent
import com.theovier.athena.client.ecs.components.transform
import ktx.ashley.allOf
import ktx.math.plus

/**
 * Update the children's position to be relative to the parent's position
 */
class ChildrenPositionSystem : SortedIteratingSystem(
    allOf(Transform::class, Parent::class).get(),
    compareBy { it.parent.hierarchyLevel }
) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val relationship = entity.parent
        val parent = relationship.entity
        if (parent !== null) {
            val parentTransform = parent.transform
            val position = parentTransform.position + transform.localPosition
            transform.position.set(position)
        }
    }
}