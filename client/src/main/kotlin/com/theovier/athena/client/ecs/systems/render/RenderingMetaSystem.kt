package com.theovier.athena.client.ecs.systems.render

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.theovier.athena.client.ecs.systems.MetaSystem
import ktx.graphics.use

class RenderingMetaSystem(private val camera: Camera, private val batch: Batch) : MetaSystem() {

    override fun update(deltaTime: Float) {
        batch.use(camera) {
            subsystems.forEach { it.update(deltaTime)}
        }

        //todo: draw everything on a buffer, apply post effect to bubble then draw to screen?
        //only when batch end (= flush to grafikkarte) gecalled wird, wird der shader applied.
        //d.h, wenn ich in einem system den shader aender und dann zuruck aender, bringt er absolut nichts.
    }
}