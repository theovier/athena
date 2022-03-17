package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ComponentTypeProviderTest {
    class ComponentA : Component

    @Test
    @DisplayName("Provider Returns an Instance of Type")
    fun providerReturnsAnInstanceOfType() {
        val provider = ComponentTypeProvider(ComponentA::class)
        assertTrue(provider.getComponent() is ComponentA)
    }

    @Test
    @DisplayName("Provider Returns New Instance Each Time")
    fun providerReturnsNewInstanceEachTime() {
        val provider = ComponentTypeProvider(ComponentA::class)
        assertNotEquals(provider.getComponent(), provider.getComponent())
    }

    @Test
    @DisplayName("Provider With Same Type Have Same Identifier")
    fun providersWithSameTypeHaveSameIdentifier() {
        val provider1 = ComponentTypeProvider(ComponentA::class)
        val provider2 = ComponentTypeProvider(ComponentA::class)
        assertEquals(provider1.identifier(), provider2.identifier())
    }
}