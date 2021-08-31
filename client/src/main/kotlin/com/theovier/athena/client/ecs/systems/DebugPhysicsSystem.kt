package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World

class DebugPhysicsSystem(private val world: World, private val camera: Camera) : EntitySystem() {
    private val renderer = Box2DDebugRenderer()

    override fun update(deltaTime: Float) {
        renderer.render(world, camera.combined)
    }
}