package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.theovier.athena.client.ecs.components.Trauma
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class TraumaComponentLoaderTest {

    @Test
    @DisplayName("Empty <Trauma> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/trauma/trauma_empty_valid")
        val component = TraumaComponentLoader().load(json)
        Assertions.assertEquals(Trauma.DEFAULT_MAX_ROTATIONAL_OFFSET, component.maxRotationalOffset)
        Assertions.assertEquals(Trauma.DEFAULT_MAX_TRANSLATIONAL_OFFSET, component.maxTranslationalOffset)
        Assertions.assertEquals(Trauma.DEFAULT_REDUCTION_FACTOR, component.reductionFactor)
    }

    @Test
    @DisplayName("<Trauma> component is loaded correctly")
    fun isComponentLoadedCorrectly() {
        val expectedMaxRotationalOffset = 2f
        val expectedMaxTranslationalOffset = 3f
        val expectedReductionFactor = 4f

        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/trauma/trauma_valid")
        val component = TraumaComponentLoader().load(json)
        Assertions.assertEquals(expectedMaxRotationalOffset, component.maxRotationalOffset)
        Assertions.assertEquals(expectedMaxTranslationalOffset, component.maxTranslationalOffset)
        Assertions.assertEquals(expectedReductionFactor, component.reductionFactor)
    }
}