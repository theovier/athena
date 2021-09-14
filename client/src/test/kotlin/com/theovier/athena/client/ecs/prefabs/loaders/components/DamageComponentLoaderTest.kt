package com.theovier.athena.client.ecs.prefabs.loaders.components

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

class DamageComponentLoaderTest {

    @Test
    @DisplayName("<Damage> component is loaded correctly")
    fun isComponentLoaded() {
        val expectedDamageAmount = 5
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/damage/damage_valid")
        val damage = DamageComponentLoader().load(json)
        Assertions.assertEquals(expectedDamageAmount, damage.damage.amount)
    }
}