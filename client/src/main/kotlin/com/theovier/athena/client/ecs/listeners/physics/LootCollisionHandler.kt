package com.theovier.athena.client.ecs.listeners.physics

import com.badlogic.ashley.core.Entity
import com.theovier.athena.client.ecs.components.Loot
import com.theovier.athena.client.ecs.components.Player
import com.theovier.athena.client.ecs.components.loot
import ktx.ashley.has

class LootCollisionHandler : AbstractCollisionHandler() {

    override fun handleCollision(contact: EntityContact) {
        val player: Entity
        val loot: Entity
        val entityA = contact.entityA
        val entityB = contact.entityB

        if (noLootEntityInvolved(entityA, entityB) || noPlayerEntityInvolved(entityA, entityB)) {
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

    private fun noLootEntityInvolved(entityA: Entity, entityB: Entity): Boolean {
        return !lootEntityInvolved(entityA, entityB)
    }

    private fun noPlayerEntityInvolved(entityA: Entity, entityB: Entity): Boolean {
        return !playerEntityInvolved(entityA, entityB)
    }

    private fun handleLootPickup(player: Entity, loot: Entity) {
        loot.loot.lootedBy = player
    }
}