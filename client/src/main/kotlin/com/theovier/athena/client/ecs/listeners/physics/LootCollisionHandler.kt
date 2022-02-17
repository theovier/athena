package com.theovier.athena.client.ecs.listeners.physics

import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.Loot
import com.theovier.athena.client.ecs.components.Looted
import com.theovier.athena.client.ecs.components.Player
import ktx.ashley.has

class LootCollisionHandler : AbstractCollisionHandler() {

    override fun handleCollision(contact: EntityContact) {
        val player: Entity
        val loot: Entity
        val entityA = contact.entityA
        val entityB = contact.entityB

        if (noLootInvolved(entityA, entityB) || noPlayerInvolved(entityA, entityB)) {
            next?.handleCollision(contact)
            return
        }

        if (entityA.has(Player.MAPPER) && entityB.has(Loot.MAPPER)) {
            player = entityA
            loot = entityB
        } else {
            player = entityB
            loot = entityA
        }
        handleLootPickup(player, loot)
    }

    private fun lootEntityInvolved(entityA: Entity, entityB: Entity): Boolean {
        return entityA.has(Loot.MAPPER) || entityB.has(Loot.MAPPER)
    }

    private fun playerEntityInvolved(entityA: Entity, entityB: Entity): Boolean {
        return entityA.has(Player.MAPPER) || entityB.has(Player.MAPPER)
    }

    private fun noLootInvolved(entityA: Entity, entityB: Entity): Boolean {
        return !lootEntityInvolved(entityA, entityB)
    }

    private fun noPlayerInvolved(entityA: Entity, entityB: Entity): Boolean {
        return !playerEntityInvolved(entityA, entityB)
    }

    private fun handleLootPickup(player: Entity, loot: Entity) {
        loot.add(
            Looted().apply {
                lootedBy = player
            }
        )
    }
}