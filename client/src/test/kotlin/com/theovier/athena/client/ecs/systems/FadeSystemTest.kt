package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.utils.GdxRuntimeException
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.entity
import ktx.ashley.with
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class FadeSystemTest {
    companion object {
        const val DELTA_TIME = 0.1f
    }

    @Test
    @DisplayName("Fade is calculated correctly")
    fun isFadedCorrectly() {
        val engine = Engine().apply {
            addSystem(FadeSystem())
        }
        val target = engine.entity {
            with<Fade> {
                start = 100f
                end = 0f
                duration = 2 * DELTA_TIME
            }
            with<Timer>()
        }
        engine.update(DELTA_TIME)
        Assertions.assertEquals(50f, target.fade.current)
        engine.update(DELTA_TIME)
        Assertions.assertEquals(0f, target.fade.current)
    }

    @Test
    @DisplayName("Entity that has been faded completly loses its <Timer> component.")
    fun isTimerComponentRemovedAfterFading() {
        val engine = Engine().apply {
            addSystem(FadeSystem())
        }
        val target = engine.entity {
            with<Fade> {
                start = 100f
                end = 0f
                duration = 2 * DELTA_TIME
            }
            with<Timer>()
        }
        Assertions.assertEquals(2, target.components.size())
        engine.update(2 * DELTA_TIME)
        Assertions.assertEquals(1, target.components.size())
    }
}