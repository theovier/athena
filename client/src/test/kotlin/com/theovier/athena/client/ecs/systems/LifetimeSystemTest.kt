package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.theovier.athena.client.ecs.components.Lifetime
import ktx.ashley.entity
import ktx.ashley.with
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class LifetimeSystemTest {
    companion object {
        const val DELTA_TIME = 0.1f
    }

    @Test
    @DisplayName("Entity with <LifeTime> is removed from engine after its lifetime")
    fun isEntityRemovedFromEngine() {
        val engine = Engine().apply {
            addSystem(LifetimeSystem())
            entity {
                with<Lifetime> {
                    duration = 0.1f
                }
            }
        }
        Assertions.assertTrue(engine.entities.size() == 1)
        engine.update(DELTA_TIME)
        Assertions.assertTrue(engine.entities.size() == 0)
    }

    @Test
    @DisplayName("Entity with <LifeTime> is not removed too fast from engine")
    fun isEntityNotRemovedTooFastFromEngine() {
        val engine = Engine().apply {
            addSystem(LifetimeSystem())
            entity {
                with<Lifetime> {
                    duration = 1.0f
                }
            }
        }
        Assertions.assertTrue(engine.entities.size() == 1)
        engine.update(DELTA_TIME)
        engine.update(DELTA_TIME)
        Assertions.assertTrue(engine.entities.size() == 1)
    }
}