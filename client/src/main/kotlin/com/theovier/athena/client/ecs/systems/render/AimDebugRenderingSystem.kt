package com.theovier.athena.client.ecs.systems.render

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.AimAssist.Companion.DEFAULT_DISTANCE
import com.theovier.athena.client.ecs.extensions.InputDrivenIteratingSystem
import ktx.ashley.allOf
import ktx.math.plus
import ktx.math.times

class AimDebugRenderingSystem(private val camera: Camera) : InputDrivenIteratingSystem(allOf(Aim::class, Spine::class).get()) {
    private val renderer = ShapeRenderer()
    private val red = Color.valueOf("#d01b3a")
    private val green = Color.valueOf("#AAFF00")

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val aim = entity.aim //todo add origin to aim?
        val spine = entity.spine
        val aimOrigin = spine.getMuzzlePosition()
        val aimDestination = aimOrigin + input.aim * DEFAULT_DISTANCE
        val correctedAimDestination = aimOrigin + aim.direction * DEFAULT_DISTANCE

        renderer.color = green
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)

        //aim assist
        renderer.line(aimOrigin, correctedAimDestination)

        //original aim
        renderer.color = red
        renderer.line(aimOrigin, aimDestination)

        renderer.end()
    }
}