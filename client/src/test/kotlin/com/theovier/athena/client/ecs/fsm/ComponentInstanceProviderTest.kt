package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ComponentInstanceProviderTest {
    class ComponentA : Component

    @Test
    @DisplayName("Provider Returns an Instance of Type")
    fun providerReturnsAnInstanceOfType() {
        val component = ComponentA()
        val provider = ComponentInstanceProvider(component)
        assertSame(provider.getComponent(), component)
    }

    @Test
    @DisplayName("Providers With Same Instance Have Same Identifier")
    fun providersWithSameInstanceHaveSameIdentifier() {
        val component = ComponentA()
        val provider1 = ComponentInstanceProvider(component)
        val provider2 = ComponentInstanceProvider(component)
        assertEquals(provider1.identifier(), provider2.identifier())
    }

    @Test
    @DisplayName("Providers With Different Instance Have Different Identifier")
    fun providersWithDifferentInstanceHaveDifferentIdentifier() {
        val provider1 = ComponentInstanceProvider(ComponentA())
        val provider2 = ComponentInstanceProvider(ComponentA())
        assertNotEquals(provider1.identifier(), provider2.identifier())
    }
}