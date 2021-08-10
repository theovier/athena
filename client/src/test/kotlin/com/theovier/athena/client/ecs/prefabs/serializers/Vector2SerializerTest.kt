package com.theovier.athena.client.ecs.prefabs.serializers

import com.badlogic.gdx.math.Vector2
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class Vector2SerializerTest {

    @Test
    @DisplayName("Vector2 is deserialized correctly")
    fun vector2IsDeserializedCorrectly() {
        val expectedVector2 = Vector2(1f, 2f)
        val container = TestContainer.load("vector2_valid")
        Assertions.assertEquals(expectedVector2, container.vector2)
    }
}