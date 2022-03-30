package com.theovier.athena.client.ecs.extensions

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.theovier.athena.client.ecs.components.Input

abstract class InputDrivenIteratingSystem : IteratingSystem {
    constructor(family: Family): super(family)
    constructor(family: Family, priority: Int): super(family, priority)

    protected lateinit var input: Input

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        input = engine.input
    }
}