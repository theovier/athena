package com.theovier.athena.client.ecs.systems.render

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.extensions.InputDrivenIteratingSystem
import ktx.ashley.allOf
import ktx.math.plus
import ktx.math.times

class AimAssistRaycastDebugSystem(private val camera: Camera) : InputDrivenIteratingSystem(allOf(Aim::class, Spine::class).get()) {
    private val renderer = ShapeRenderer()
    private val red = Color.valueOf("#d01b3a")

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val assist = entity.aimAssist
        val spine = entity.spine
        val originalAimDirection = input.aim

        renderer.color = red
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)

        val start = spine.getMuzzlePosition()

        for (angle in 0..assist.maxAngle step assist.degreesBetweenAngleChecks) {
            for (sign in intArrayOf(-1, 1)) {
                val newAngle = angle * sign
                val direction = originalAimDirection.cpy()
                direction.rotateDeg(newAngle.toFloat())
                val end = start + direction * assist.distance
                renderer.line(start, end)
            }
        }

        renderer.end()
    }
}