package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.utils.Array

//system of systems
abstract class MetaSystem : EntitySystem() {
    protected val subsystems = Array<EntitySystem>()

    fun addSubsystem(system: EntitySystem) {
        subsystems.add(system)
        if (engine != null) {
            system.addedToEngine(engine)
        }
    }

    override fun addedToEngine(engine: Engine) {
        subsystems.forEach { it.addedToEngine(engine) }
    }

    override fun removedFromEngine(engine: Engine) {
        subsystems.forEach { it.removedFromEngine(engine) }
    }
}