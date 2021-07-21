package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import ktx.app.KtxInputAdapter
import ktx.ashley.allOf
import ktx.math.plus
import ktx.math.times

class PlayerMovementSystem : KtxInputAdapter,
    IteratingSystem(allOf(Player::class, Movement::class, Transform::class).get()) {
    private var direction = Vector2()

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        (Gdx.input.inputProcessor as InputMultiplexer).addProcessor(this)
    }

    override fun removedFromEngine(engine: Engine?) {
        super.removedFromEngine(engine)
        (Gdx.input.inputProcessor as InputMultiplexer).removeProcessor(this)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val movement = entity.movement
        val velocity = direction.nor() * movement.speed
        transform.position.set(transform.position + velocity * deltaTime)
    }

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.W -> {
                if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    direction.y = 0f
                } else {
                    direction.y = 1f
                }
            }
            Input.Keys.A -> {
                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    direction.x = 0f
                } else {
                    direction.x = -1f
                }
            }
            Input.Keys.S -> {
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    direction.y = 0f
                } else {
                    direction.y = -1f
                }
            }
            Input.Keys.D -> {
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    direction.x = 0f
                } else {
                    direction.x = 1f
                }
            }
            else -> return false
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.W -> {
                if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    direction.y = -1f
                } else {
                    direction.y = 0f
                }
            }
            Input.Keys.A -> {
                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    direction.x = 1f
                } else {
                    direction.x = 0f
                }
            }
            Input.Keys.S -> {
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    direction.y = 1f
                } else {
                    direction.y = 0f
                }
            }
            Input.Keys.D -> {
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    direction.x = -1f
                } else {
                    direction.x = 0f
                }
            }
            else -> return false
        }
        return true
    }
}