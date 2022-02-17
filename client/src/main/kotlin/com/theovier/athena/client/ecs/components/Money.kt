package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.has
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

val Entity.money: Money
    get() = this[Money.MAPPER] ?: throw GdxRuntimeException("Money for entity '$this' is null")

val Entity.hasMoneyComponent: Boolean
    get() = this.has(Money.MAPPER)