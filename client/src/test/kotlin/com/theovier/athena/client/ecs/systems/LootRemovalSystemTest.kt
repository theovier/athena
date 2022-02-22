package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.theovier.athena.client.ecs.components.Loot
import com.theovier.athena.client.ecs.components.Looted
import com.theovier.athena.client.ecs.systems.loot.LootRemovalSystem
import ktx.ashley.entity
import ktx.ashley.with
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class LootRemovalSystemTest {
    companion object {
        const val DELTA_TIME = 0.1f
    }

    @Test
    @DisplayName("Entity with <Looted> is removed from engine.")
    fun isEntityRemovedFromEngine() {
        val engine = Engine().apply {
            addSystem(LootRemovalSystem())
            entity {
                with<Loot>()
                with<Looted>()
            }
        }
        Assertions.assertTrue(engine.entities.size() == 1)
        engine.update(DELTA_TIME)
        Assertions.assertTrue(engine.entities.size() == 0)
    }
}