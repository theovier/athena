package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class TransformComponentLoaderTest {

    @Test
    @DisplayName("<Transform> component is loaded correctly")
    fun isTransformComponentLoadedCorrectly() {
        val expectedPosition = Vector3(1f, 2f, 0f)
        val expectedSize = Vector2(3f, 4f)
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/transform/transform_valid")
        val transform = TransformComponentLoader().load(json)
        Assertions.assertTrue(transform.position == expectedPosition)
        Assertions.assertTrue(transform.size == expectedSize)
    }

    @Test
    @DisplayName("Empty <Transform> component is loaded correctly")
    fun isEmptyTransformComponentLoadedCorrectly() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/transform/transform_empty_valid")
        val transform = TransformComponentLoader().load(json)
        Assertions.assertTrue(transform.position == Vector3.Zero)
        Assertions.assertTrue(transform.size == Vector2(1f, 1f))
    }

    @Test
    @DisplayName("Unrecognizable elements are ignored")
    fun areUnrecognizableElementsIgnored() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/transform/transform_noise_valid")
        val transform = TransformComponentLoader().load(json)
        Assertions.assertTrue(transform.position == Vector3.Zero)
        Assertions.assertTrue(transform.size == Vector2(1f, 1f))
    }
}