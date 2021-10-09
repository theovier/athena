package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.CameraTarget
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class CameraTargetComponentLoader : ComponentLoader {
    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): CameraTarget {
        return CameraTarget()
    }
}