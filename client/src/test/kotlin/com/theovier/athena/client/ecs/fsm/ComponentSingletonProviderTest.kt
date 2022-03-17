package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ComponentSingletonProviderTest {
    class ComponentA : Component

    @Test
    @DisplayName("Provider Returns an Instance of Type")
    fun providerReturnsAnInstanceOfType() {
        val provider = ComponentSingletonProvider(ComponentA::class.java)
        assertTrue(provider.getComponent() is ComponentA)
    }

    @Test
    @DisplayName("Provider Returns Same Instance Each Time")
    fun providerReturnsSameInstanceEachTime() {
        val provider = ComponentSingletonProvider(ComponentA::class.java)
        assertEquals(provider.getComponent(), provider.getComponent())
    }

    @Test
    @DisplayName("Providers With Same Type Have Different Identifier")
    fun providersWithSameTypeHaveDifferentIdentifier() {
        val provider1 = ComponentSingletonProvider(ComponentA::class.java)
        val provider2 = ComponentSingletonProvider(ComponentA::class.java)
        assertNotEquals(provider1.identifier(), provider2.getComponent())
    }
}