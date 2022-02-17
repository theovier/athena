package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

class Money : Component, Poolable {
    var amount = 0

    override fun reset() {
        amount = DEFAULT_AMOUNT
    }

    companion object {
        val MAPPER = mapperFor<Money>()
        const val DEFAULT_AMOUNT = 0
    }
}