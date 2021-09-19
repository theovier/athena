package com.theovier.athena.client.ecs.listeners.damage

interface DamageEventSource {
    fun addDamageListener(listener: DamageListener)
    fun removeDamageListener(listener: DamageListener)
}