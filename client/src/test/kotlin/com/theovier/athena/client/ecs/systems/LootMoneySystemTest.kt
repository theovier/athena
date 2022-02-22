package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.systems.loot.LootMoneySystem
import ktx.ashley.entity
import ktx.ashley.with
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class LootMoneySystemTest {
    companion object {
        const val DELTA_TIME = 0.1f
    }

    @Test
    @DisplayName("Positive money amount is correctly added")
    fun isMoneyCorrectlyAdded() {
        val expectedAmountBefore = 10
        val expectedAmountAfter = 13
        val looter = Entity().apply {
            add(
                Money().apply {
                amount = expectedAmountBefore
            })
        }

        val engine = Engine().apply {
            addSystem(LootMoneySystem())
            addEntity(looter)
            entity {
                with<Money> {
                    amount = 3
                }
                with<Loot> {

                }
                with<Looted> {
                    lootedBy = looter
                }
            }
        }
        Assertions.assertEquals(looter.money.amount, expectedAmountBefore)
        engine.update(DELTA_TIME)
        Assertions.assertEquals(looter.money.amount, expectedAmountAfter)
    }

    @Test
    @DisplayName("Negative amount is correctly subtracted")
    fun isMoneyCorrectlySubtracted() {
        val expectedAmountBefore = 0
        val expectedAmountAfter = -10
        val looter = Entity().apply {
            add(
                Money().apply {
                    amount = expectedAmountBefore
                })
        }

        val engine = Engine().apply {
            addSystem(LootMoneySystem())
            addEntity(looter)
            entity {
                with<Money> {
                    amount = -10
                }
                with<Loot> {

                }
                with<Looted> {
                    lootedBy = looter
                }
            }
        }
        Assertions.assertEquals(looter.money.amount, expectedAmountBefore)
        engine.update(DELTA_TIME)
        Assertions.assertEquals(looter.money.amount, expectedAmountAfter)
    }
}