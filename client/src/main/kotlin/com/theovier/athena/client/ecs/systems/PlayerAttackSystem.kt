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
import ktx.app.KtxInputAdapter
import ktx.ashley.allOf
import ktx.ashley.entity
import ktx.ashley.with
import ktx.math.minus
import mu.KotlinLogging


private val log = KotlinLogging.logger {}
class PlayerAttackSystem(private val game: AthenaGame, private val viewport: Viewport) : KtxInputAdapter, IteratingSystem(allOf(Player::class).get()) {

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

            val origin = Vector2(playerPosition.x, playerPosition.y)
            val destination = viewport.unproject(latestClickedPosition)
            val bulletDirection = (destination - origin).nor()

            engine.entity {
                with<Transform> {
                    position.set(playerPosition.x, playerPosition.y, 0f)
                    size.set(0.6f, 0.6f)
                    rotation = bulletDirection.angleDeg()
                }
                with<SpriteRenderer> {
                    val texture: Texture = game.assetStorage["sprites/bullet.png"]
                    sprite.setRegion(texture)
                    sprite.setSize(texture.width.toFloat(), texture.height.toFloat())
                }
                with<Movement> {
                    maxSpeed = 20f
                    accelerationFactor = 250f
                    decelerationFactor = 10f
                    direction = bulletDirection
                }
            }
            wantsToFire = false
        }
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