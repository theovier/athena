package com.theovier.athena.client.ecs.prefabs

import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.GdxNativesLoader
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.get
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.FileNotFoundException

class PrefabTest {

    @Test
    @DisplayName("Multiple components are loaded correctly from prefab")
    fun multipleComponentsAreCorrectlyLoadedFromPrefab() {
        val entity = Prefab.instantiate("multiple")
        val playerComponent = entity.get<Player>()
        val cameraTargetComponent = entity.get<CameraTarget>()
        val lifetimeComponent = entity.get<Lifetime>()
        Assertions.assertNotNull(playerComponent)
        Assertions.assertNotNull(cameraTargetComponent)
        Assertions.assertNotNull(lifetimeComponent)
        Assertions.assertTrue(entity.components.size() > 1)
    }

    @Test
    @DisplayName("Runtime error is raised when invalid path to prefab is given")
    fun isErrorRaisedWhenInvalidPrefabPathGiven() {
        Assertions.assertThrows(FileNotFoundException::class.java) {
            Prefab.instantiate("invalid")
        }
    }

    @Test
    @DisplayName("<CameraTarget> component is loaded from prefab correctly")
    fun isCameraTargetComponentCorrectlyLoadedFromPrefab() {
        val entity = Prefab.instantiate("cameraTarget")
        val component = entity.get<CameraTarget>()
        Assertions.assertNotNull(component)
        Assertions.assertTrue(entity.components.size() == 1)
    }

    @Test
    @DisplayName("<Lifetime> component is loaded from prefab correctly")
    fun isLifetimeComponentCorrectlyLoadedFromPrefab() {
        val expectedDuration = 5f
        val entity = Prefab.instantiate("lifetime")
        val component = entity.get<Lifetime>()
        Assertions.assertNotNull(component)
        Assertions.assertTrue(entity.components.size() == 1)
        Assertions.assertEquals(component?.duration, expectedDuration)
    }

    @Test
    @DisplayName("<Movement> component is loaded from prefab correctly")
    fun isMovementComponentCorrectlyLoadedFromPrefab() {
        val entity = Prefab.instantiate("movement")
        val component = entity.get<Movement>()
        Assertions.assertNotNull(component)
        Assertions.assertTrue(entity.components.size() == 1)
    }

    @Test
    @DisplayName("<Player> component is loaded from prefab correctly")
    fun isPlayerComponentCorrectlyLoadedFromPrefab() {
        val entity = Prefab.instantiate("player")
        val component = entity.get<Player>()
        Assertions.assertNotNull(component)
        Assertions.assertTrue(entity.components.size() == 1)
    }

    @Test
    @DisplayName("<SpriteRenderer> component is loaded from prefab correctly")
    fun isSpriteRendererComponentCorrectlyLoadedFromPrefab() {
        val entity = Prefab.instantiate("spriteRenderer")
        val component = entity.get<SpriteRenderer>()
        Assertions.assertNotNull(component)
        Assertions.assertTrue(entity.components.size() == 1)
    }

    @Test
    @DisplayName("<Transform> component is loaded from prefab correctly")
    fun isTransformComponentCorrectlyLoadedFromPrefab() {
        val expectedPosition = Vector3(1f, 2f, 3f)
        val entity = Prefab.instantiate("transform")
        val component = entity.get<Transform>()
        Assertions.assertNotNull(component)
        Assertions.assertTrue(entity.components.size() == 1)
        val actualPosition = component?.position
        Assertions.assertEquals(expectedPosition.x, actualPosition?.x)
        Assertions.assertEquals(expectedPosition.y, actualPosition?.y)
        Assertions.assertEquals(expectedPosition.z, actualPosition?.z)
    }

    @Test
    @DisplayName("<Aim> component is loaded from prefab correctly")
    fun isAimComponentCorrectlyLoadedFromPrefab() {
        val entity = Prefab.instantiate("aim")
        val component = entity.get<Aim>()
        Assertions.assertNotNull(component)
        Assertions.assertTrue(entity.components.size() == 1)
    }

    @Test
    @DisplayName("<Crosshair> component is loaded from prefab correctly")
    fun isCrosshairComponentCorrectlyLoadedFromPrefab() {
        val entity = Prefab.instantiate("crosshair")
        val component = entity.get<Crosshair>()
        Assertions.assertNotNull(component)
        Assertions.assertTrue(entity.components.size() == 1)
    }

    @Test
    @DisplayName("<Particle> component is loaded from prefab correctly")
    fun isParticleComponentCorrectlyLoadedFromPrefab() {
        GdxNativesLoader.load()
        val entity = Prefab.instantiate("particle")
        val component = entity.get<Particle>()
        Assertions.assertNotNull(component)
        Assertions.assertTrue(entity.components.size() == 1)
    }
}