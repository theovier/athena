package com.theovier.athena.client.ecs.systems.spawn

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector3
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.damage.HitMarker
import com.theovier.athena.client.ecs.components.damage.Impact
import com.theovier.athena.client.ecs.components.damage.impact
import com.theovier.athena.client.ecs.prefabs.Prefab
import ktx.ashley.allOf

class ImpactSpawnSystem : IteratingSystem(allOf(Impact::class, HitMarker::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val impact = entity.impact
        val prefabName = impact.prefabToSpawn
        Prefab.instantiate(prefabName) {
            add(Transform())
            with(this.transform) {
                this.position.set(Vector3(impact.position))
                this.rotation = impact.angle
            }
        }
    }
}