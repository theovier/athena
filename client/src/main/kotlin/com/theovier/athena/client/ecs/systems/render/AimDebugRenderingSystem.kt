package com.theovier.athena.client.ecs.systems.render

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.esotericsoftware.spine.attachments.PointAttachment
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf
import ktx.math.plus
import ktx.math.times

class AimDebugRenderingSystem(private val camera: Camera) : IteratingSystem(allOf(Player::class, Transform::class, Aim::class, Spine::class).get()) {
    private val renderer = ShapeRenderer()
    private val distance = 10f
    private val color = Color.valueOf("#d01b3a")

    override fun processEntity(player: Entity, deltaTime: Float) {
        val transform = player.transform
        val aim = player.aim
        val spine = player.spine
        val skeleton = spine.skeleton

        val weaponBone = skeleton.findBone("weapon")
        val muzzlePointSlot = skeleton.findSlot("bullet_spawn")
        val muzzlePointAttachment = muzzlePointSlot.attachment as PointAttachment
        val muzzlePosition = muzzlePointAttachment.computeWorldPosition(weaponBone, Vector2(muzzlePointAttachment.x, muzzlePointAttachment.y))
        val rotation = muzzlePointAttachment.computeWorldRotation(weaponBone)
        val baseDirection = Vector2(1f,0f)
        val shootingDirection = baseDirection.rotateDeg(rotation)

        renderer.color = color
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.line(muzzlePosition, muzzlePosition + shootingDirection * distance)
        renderer.end()
    }
}