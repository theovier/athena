package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Money
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class MoneyComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Money {
        val amount = componentJSON.getInt("amount", 0)
        return Money().apply {
            this.amount = amount
        }
    }
}