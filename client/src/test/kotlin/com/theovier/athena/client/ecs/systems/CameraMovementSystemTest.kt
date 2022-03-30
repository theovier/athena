package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.GdxNativesLoader
import com.theovier.athena.client.ecs.components.CameraTarget
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.movement.Velocity
import com.theovier.athena.client.ecs.systems.camera.CameraMovementSystem
import com.theovier.athena.client.ecs.systems.movement.MovementSystem
import ktx.ashley.entity
import ktx.ashley.with
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CameraMovementSystemTest {
    private var camera = OrthographicCamera()

    companion object {
        const val DELTA_TIME = 0.1f
        val STARTING_POSITION = Vector3(1f, 1f, 0f)
    }

    @BeforeAll
    fun setup() {
        GdxNativesLoader.load()
    }

    @BeforeEach
    fun reset() {
        camera = OrthographicCamera()
        camera.position.set(STARTING_POSITION)
    }

    @Test
    @DisplayName("Camera follows entity with <CameraTarget> component")
    fun cameraFollowsTarget() {
        val engine = Engine().apply {
            addSystem(MovementSystem())
            addSystem(CameraMovementSystem(camera, camera))
            entity{
                with<CameraTarget>()
                with<Transform> {
                    position.set(STARTING_POSITION)
                }
                with<Velocity> {
                    velocity.set(1f, 0f)
                }
            }
        }
        engine.update(DELTA_TIME * 20) //simulate multiple frames at once
        Assertions.assertTrue(camera.position != STARTING_POSITION)
    }

    @Test
    @DisplayName("Camera does not move when entity with <CameraTarget> component is standing still")
    fun cameraDoesNotMoveWhenTargetIsStandingStill() {
        val engine = Engine().apply {
            addSystem(CameraMovementSystem(camera, camera))
            entity {
                with<CameraTarget>()
                with<Transform> {
                    position.set(STARTING_POSITION)
                }
            }
        }
        engine.update(DELTA_TIME)
        Assertions.assertTrue(camera.position == STARTING_POSITION)
    }
}