package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.theovier.athena.client.AthenaGame
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.prefabs.Prefab
import ktx.app.KtxInputAdapter
import ktx.ashley.allOf
import ktx.ashley.entity
import ktx.ashley.with
import ktx.math.minus

class PlayerAttackSystem(private val viewport: Viewport) : KtxInputAdapter, IteratingSystem(allOf(Player::class).get()) {

    private var wantsToFire = false
    private var latestClickedPosition = Vector2()

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        (Gdx.input.inputProcessor as InputMultiplexer).addProcessor(this)
    }

    override fun removedFromEngine(engine: Engine?) {
        super.removedFromEngine(engine)
        (Gdx.input.inputProcessor as InputMultiplexer).removeProcessor(this)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (wantsToFire) {
            val playerPosition = entity.transform.position
            val bulletOrigin = Vector2(playerPosition.x, playerPosition.y)
            spawnBullet(bulletOrigin)
            wantsToFire = false
        }
    }

    private fun spawnBullet(origin: Vector2) {
        val destination = viewport.unproject(latestClickedPosition)
        val bulletDirection = (destination - origin).nor()
        val bullet = Prefab.instantiate("bullet")
        with (bullet.transform) {
            rotation = bulletDirection.angleDeg()
            position.set(origin.x, origin.y, 0f)
        }
        bullet.movement.direction = bulletDirection
        engine.addEntity(bullet)
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        when (button) {
            Input.Buttons.LEFT -> {
                latestClickedPosition = Vector2(screenX.toFloat(), screenY.toFloat())
                wantsToFire = true
            }
            else -> return false
        }
        return true
    }
}