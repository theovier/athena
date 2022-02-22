package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.theovier.athena.client.ecs.components.Money
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MoneyComponentLoaderTest {

    @Test
    @DisplayName("Empty <Money> component is loaded correctly")
    fun isEmptyComponentLoaded() {
        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/money/money_empty_valid")
        val component = MoneyComponentLoader().load(json)
        Assertions.assertEquals(Money.DEFAULT_AMOUNT, component.amount)
    }

    @Test
    @DisplayName("<Money> component is loaded correctly")
    fun isComponentLoadedCorrectly() {
        val expectedAmount = 3

        val json = ComponentLoaderUtils.loadComponentJSONFromFile("components/money/money_valid")
        val component = MoneyComponentLoader().load(json)
        Assertions.assertEquals(expectedAmount, component.amount)
    }
}