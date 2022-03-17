package com.theovier.athena.client.ecs.fsm

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.signals.Listener
import com.badlogic.ashley.signals.Signal
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EntityStateMachineTest {
    class ComponentA : Component
    class ComponentB : Component

    private var entity = Entity()
    private var fsm = EntityStateMachine(entity)

    @BeforeEach
    fun setup() {
        entity = Entity()
        fsm = EntityStateMachine(entity)
    }

    @Test
    @DisplayName("Create State Does Add State")
    fun createStateDoesAddState() {
        fsm.createState("test")
            .add(ComponentA::class.java)
        fsm.changeState("test")
        assertEquals(1, entity.components.count())
        assertTrue(entity.components.get(0) is ComponentA)
    }

    @Test
    @DisplayName("Enter State Adds States Components")
    fun enterStateAddsStatesComponents() {
        val state = EntityState()
        val componentA = ComponentA()
        val mapperA = ComponentMapper.getFor(ComponentA::class.java)
        state.add(ComponentA::class.java).withInstance(componentA)
        fsm.addState("test", state)
        assertEquals(0, entity.components.count())
        fsm.changeState("test")
        assertEquals(mapperA[entity], componentA)
        assertEquals(1, entity.components.count())
    }

    @Test
    @DisplayName("Enter Second State Adds Second States Components")
    fun enterSecondStateAddsSecondStatesComponents() {
        val state1 = EntityState()
        val state2 = EntityState()
        val componentA = ComponentA()
        val componentB = ComponentB()
        val mapperB = ComponentMapper.getFor(ComponentB::class.java)
        state1.add(ComponentA::class.java).withInstance(componentA)
        state2.add(ComponentB::class.java).withInstance(componentB)
        fsm.addState("test1", state1)
        fsm.addState("test2", state2)
        fsm.changeState("test1")
        fsm.changeState("test2")
        assertSame(mapperB[entity], componentB)
    }

    @Test
    @DisplayName("Enter Second State Removes First States Components")
    fun enterSecondStateRemovesFirstStatesComponents() {
        val state1 = EntityState()
        val state2 = EntityState()
        val componentA = ComponentA()
        val componentB = ComponentB()
        val mapperA = ComponentMapper.getFor(ComponentA::class.java)
        state1.add(ComponentA::class.java).withInstance(componentA)
        state2.add(ComponentB::class.java).withInstance(componentB)
        fsm.addState("test1", state1)
        fsm.addState("test2", state2)
        fsm.changeState("test1")
        fsm.changeState("test2")
        assertFalse(mapperA.has(entity))
    }

    @Test
    @DisplayName("Enter Second State Does Not Remove Overlapping Components")
    fun enterSecondStateDoesNotRemoveOverlappingComponents() {
        class MyListener: Listener<Entity> {
            override fun receive(signal: Signal<Entity>?, `object`: Entity?) {
                fail<Any>("Should not remove overlapping components")
            }
        }
        entity.componentRemoved.add(MyListener())
        val componentA = ComponentA()
        val componentB = ComponentB()
        val mapperA = ComponentMapper.getFor(ComponentA::class.java)
        val state1 = EntityState()
        val state2 = EntityState()

        state1.add(ComponentA::class.java).withInstance(componentA)
        state2.add(ComponentA::class.java).withInstance(componentA)
        state2.add(ComponentB::class.java).withInstance(componentB)

        fsm.addState("test1", state1)
        fsm.addState("test2", state2)

        fsm.changeState("test1")
        fsm.changeState("test2")
        assertSame(mapperA[entity], componentA)
    }

    @Test
    @DisplayName("Enter Second State Removes Different Components Of Same Type")
    fun enterSecondStateRemovesDifferentComponentsOfSameType() {
        val state1 = EntityState()
        val componentA = ComponentA()
        val mapperA = ComponentMapper.getFor(ComponentA::class.java)
        state1.add(ComponentA::class.java).withInstance(componentA)
        fsm.addState("test1", state1)
        fsm.changeState("test1")
        val state2 = EntityState()
        val componentA2 = ComponentA()
        val componentB = ComponentB()
        state2.add(ComponentA::class.java).withInstance(componentA2)
        state2.add(ComponentB::class.java).withInstance(componentB)
        fsm.addState("test2", state2)
        fsm.changeState("test2")
        assertSame(mapperA[entity], componentA2)
    }
}