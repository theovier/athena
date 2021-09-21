package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.theovier.athena.client.weapons.Damage
import ktx.ashley.get
import ktx.ashley.has
import ktx.ashley.mapperFor
import java.util.*

class HitMarker : Component {
    val hits: MutableList<Damage> = LinkedList()

    fun onHit(damage: Damage) {
        hits.add(damage)
    }

    companion object {
        val MAPPER = mapperFor<HitMarker>()
    }
}

val Entity.hitmarker: HitMarker
    get() = this[HitMarker.MAPPER] ?: throw GdxRuntimeException("HitMarker for entity '$this' is null")

val Entity.hasHitMarkerComponent: Boolean
    get() = this.has(HitMarker.MAPPER)

val Entity.hasNoHitMarkerComponent: Boolean
    get() = !this.hasHitMarkerComponent