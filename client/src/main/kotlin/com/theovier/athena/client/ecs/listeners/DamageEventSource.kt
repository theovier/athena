package com.theovier.athena.client.ecs.listeners

interface DamageEventSource {
    fun addDamageListener(listener: DamageListener)
    fun removeDamageListener(listener: DamageListener)
}