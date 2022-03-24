package com.theovier.athena.client.ecs.prefabs.loaders.components.animation.controllers

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.animation.PlayerAnimationController
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool
import com.theovier.athena.client.ecs.prefabs.loaders.components.ComponentLoader

class PlayerAnimationControllerComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): PlayerAnimationController {
        return PlayerAnimationController()
    }
}