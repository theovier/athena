package com.theovier.athena.client.ecs.prefabs.serializers

import com.badlogic.gdx.math.Vector3
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class Vector3SerializerTest {

    @Test
    @DisplayName("Vector3 is deserialized correctly")
    fun vector3IsDeserializedCorrectly() {
        val expectedVector3 = Vector3(1f, 2f, 3f)
        val container = TestContainer.load("vector3_valid")
        Assertions.assertEquals(expectedVector3, container.vector3)
    }
}