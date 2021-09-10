package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import org.junit.Test
import org.junit.jupiter.api.Assertions

class ComponentLoaderUtils {
    companion object {

        /** assumes the component is the only child of the root */
        fun loadComponentJSONFromFile(name: String): JsonValue {
            val path = "/prefabs/$name.json"
            val stream = ComponentLoaderUtils::class.java.getResourceAsStream(path)
            val jsonReader = JsonReader()
            val root = jsonReader.parse(stream)
            return root.child
        }
    }

    @Test
    fun isLoadingFile() {
        Assertions.assertDoesNotThrow {
            loadComponentJSONFromFile("components/transform/transform_valid")
        }
    }
}