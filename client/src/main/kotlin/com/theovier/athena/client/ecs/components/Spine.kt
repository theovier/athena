package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.esotericsoftware.spine.*
import ktx.ashley.get
import ktx.ashley.mapperFor
import com.esotericsoftware.spine.Skeleton
import com.esotericsoftware.spine.attachments.PointAttachment
import ktx.ashley.has

class Spine : Component {
    lateinit var skeleton: Skeleton
    lateinit var state: AnimationState
    val weaponBone: Bone
        get() = skeleton.findBone("weapon")
    var damageIndicatorSlotNames = arrayOf<String>()
    val hasDamageIndicator: Boolean
        get() = damageIndicatorSlotNames.isNotEmpty()
    val hasNoDamageIndicator: Boolean
        get() = !hasDamageIndicator

    fun getRandomDamageIndicatorPosition() : Vector2 {
        val name = damageIndicatorSlotNames.random()
        val slot = skeleton.findSlot(name)
        val point = slot.attachment as PointAttachment
        return point.computeWorldPosition(skeleton.rootBone, Vector2(point.x, point.y))
    }

    companion object {
        val MAPPER = mapperFor<Spine>()
    }
}

val Entity.spine: Spine
    get() = this[Spine.MAPPER] ?: throw GdxRuntimeException("Spine for entity '$this' is null")

val Entity.hasSpineComponent: Boolean
    get() = this.has(Spine.MAPPER)