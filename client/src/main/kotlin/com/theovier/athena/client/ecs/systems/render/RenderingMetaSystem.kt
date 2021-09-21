package com.theovier.athena.client.ecs.systems.render

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.Batch
import com.theovier.athena.client.ecs.systems.MetaSystem
import ktx.graphics.use

class RenderingMetaSystem(private val camera: Camera, private val batch: Batch) : MetaSystem() {

    override fun update(deltaTime: Float) {
        batch.use(camera) {
            subsystems.forEach { it.update(deltaTime)}
        }
    }
}