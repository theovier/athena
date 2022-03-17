package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EntityStateTest {
    class ComponentA : Component
    class ComponentB : Component {
        var value = 0f
    }
    class CustomBProvider : ComponentProvider<ComponentB> {
        override fun getComponent(): ComponentB {
            return ComponentB().apply {
                value = 5f
            }
        }

        override fun identifier(): Any {
            return ComponentB::class.java
        }
    }

    private var state = EntityState()

    @BeforeEach
    fun setup() {
        state = EntityState()
    }

    @Test
    @DisplayName("Add With No Qualifier Creates Type Provider")
    fun addWithNoQualifierCreatesTypeProvider() {
        state.add(ComponentA::class.java)
        val provider = state.providers.get(ComponentA::class.java)
        assertTrue(provider is ComponentTypeProvider)
        assertTrue(provider.getComponent() is ComponentA)
    }

    @Test
    @DisplayName("Add With Type Qualifier Creates Type Provider")
    fun addWithTypeQualifierCreatesTypeProvider() {
        state.add(ComponentA::class.java).withType()
        val provider = state.providers.get(ComponentA::class.java)
        assertTrue(provider is ComponentTypeProvider)
        assertTrue(provider.getComponent() is ComponentA)
    }

    @Test
    @DisplayName("Add With Instance Qualifier Creates Instances Provider")
    fun addWithInstanceQualifierCreatesInstanceProvider() {
        val component = ComponentA()
        state.add(ComponentA::class.java).withInstance(component)
        val provider = state.providers.get(ComponentA::class.java)
        assertTrue(provider is ComponentInstanceProvider)
        assertEquals(provider.getComponent(), component)
    }

    @Test
    @DisplayName("Add With Singleton Qualifier Creates Singleton Provider")
    fun addWithSingletonQualifierCreatesSingletonProvider() {
        state.add(ComponentA::class.java).withSingleton(ComponentA::class.java)
        val provider = state.providers.get(ComponentA::class.java)
        assertTrue(provider is ComponentSingletonProvider)
        assertTrue(provider.getComponent() is ComponentA)
    }

    @Test
    @DisplayName("Add With Custom Provider Creates Custom Provider")
    fun addWithCustomProviderCreatesCustomProvider() {
        val bProvider = CustomBProvider()
        state.add(ComponentB::class.java).withProvider<Component>(bProvider)
        assertTrue(bProvider is CustomBProvider)
        assertTrue(bProvider.getComponent() is ComponentB)
        assertEquals(5f, bProvider.getComponent().value)
    }
}