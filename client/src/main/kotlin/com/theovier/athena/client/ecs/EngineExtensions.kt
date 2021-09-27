package com.theovier.athena.client.ecs

import com.badlogic.ashley.core.Engine
import com.theovier.athena.client.ecs.components.Input
import ktx.ashley.allOf
import ktx.ashley.get

//ugly hack as we cannot add new fields to the Engine class
val Engine.input: Input
    get() = this.getEntitiesFor(
        allOf(Input::class).get()
    )
        .first()[Input.MAPPER]!!