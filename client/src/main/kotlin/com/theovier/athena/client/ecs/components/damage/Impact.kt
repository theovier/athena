package com.theovier.athena.client.ecs.components.damage

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.has
import ktx.ashley.mapperFor

class Impact : Component {
    var angle = 0.0f
    var position = Vector3()
    var prefabToSpawn = ""

    companion object {
        val MAPPER = mapperFor<Impact>()
    }
}

val Entity.impact: Impact
    get() = this[Impact.MAPPER] ?: throw GdxRuntimeException("Impact for entity '$this' is null")

val Entity.hasImpactComponent: Boolean
    get() = this.has(Impact.MAPPER)

val Entity.hasNoImpactComponent: Boolean
    get() = !this.hasImpactComponent