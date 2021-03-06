package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.movement.Dash
import com.theovier.athena.client.ecs.components.movement.Velocity
import com.theovier.athena.client.ecs.components.movement.dash
import com.theovier.athena.client.ecs.components.movement.velocity
import com.theovier.athena.client.ecs.systems.player.PlayerDashSystem
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.koin.test.junit5.AutoCloseKoinTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DashSystemTest : AutoCloseKoinTest() {

    private val input = Input().apply {
        dash = true
    }
    private val entity = Entity()
        .add(Player())
        .add(Transform())
        .add(Velocity())
        .add(Dash())
    val engine = PooledEngine()

    @BeforeAll
    fun setup() {
        engine.apply {
            addEntity(
                Entity().add(input)
            )
            addEntity(entity)
            addSystem(PlayerDashSystem())
        }
    }

    @BeforeEach
    fun reset() {
        //simulate button press for each test as dash system resets this
        input.dash = true
        entity.dash.reset()
    }

    @Test
    fun isCurrentlyDashingIsTrueDuringDash() {
        entity.dash.timeLeft = 0.2f
        entity.dash.duration = 0.2f

        assertFalse(entity.dash.isCurrentlyDashing)
        engine.update(0.1f)
        assertTrue(entity.dash.isCurrentlyDashing)
    }

    @Test
    fun isCurrentlyDashingIsFalseAfterDash() {
        entity.dash.timeLeft = 0.2f
        entity.dash.duration = 0.2f

        engine.update(0.2f)
        assertFalse(entity.dash.isCurrentlyDashing)
    }

    @Test
    fun dashSetsVelocityCorrectly() {
        entity.transform.forward.set(Vector2(1f, 0f))
        entity.velocity.velocity = Vector2(0f, 0f)
        entity.dash.speed = 100f
        val expectedVelocity = Vector2(100f, 0f)

        engine.update(0.1f)

        assertEquals(expectedVelocity, entity.velocity.velocity)
    }

    @Test
    fun dashSpawnsCorrectPrefab() {
        entity.dash.prefabToSpawn = "emptyEntity"
        assertEquals(2, engine.entities.size()) //entity + input

        assertThrows(ExceptionInInitializerError::class.java) {
            /*
                the system will try to call Prefab.instantiate which will fail because the required Koin
                instance is not started. If we get this error, we know that Prefab.instantiate() was called.
            */
            engine.update(0.1f)
        }
    }

    @Test
    fun dashTakesCorrectTime() {
        entity.dash.timeLeft = 0.5f

        assertFalse(entity.dash.isCurrentlyDashing)
        engine.update(0.1f)
        assertTrue(entity.dash.isCurrentlyDashing)
        engine.update(0.4f)
        assertFalse(entity.dash.isCurrentlyDashing)
    }

    @Test
    fun cannotDashDuringCooldown() {
        entity.dash.canNextDashInSeconds = 10f

        assertFalse(entity.dash.canDash)
        engine.update(0.1f)
        assertFalse(entity.dash.canDash)
    }

    @Test
    fun dashCoolDownIsReset() {
        entity.dash.canNextDashInSeconds = 10f

        assertFalse(entity.dash.canDash)
        engine.update(10f)
        assertTrue(entity.dash.canDash)
    }


}