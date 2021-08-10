package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.inputs.XboxInputAdapter
import ktx.app.KtxInputAdapter
import ktx.ashley.allOf
import mu.KotlinLogging

private val log = KotlinLogging.logger {}
class PlayerMovementSystem : KtxInputAdapter, XboxInputAdapter, IteratingSystem(allOf(Player::class, Movement::class).get()) {
    private var direction = Vector2()
    private lateinit var currentController: Controller

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        (Gdx.input.inputProcessor as InputMultiplexer).addProcessor(this)
        currentController = Controllers.getCurrent()
        currentController.addListener(this)
    }

    override fun removedFromEngine(engine: Engine?) {
        super.removedFromEngine(engine)
        (Gdx.input.inputProcessor as InputMultiplexer).removeProcessor(this)
        currentController.removeListener(this)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val playerMovement = entity.movement
        playerMovement.direction = direction
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

    override fun axisMoved(controller: Controller?, axis: Int, value: Float): Boolean {
        if (XboxInputAdapter.isAxisValueInDeadzone(value)) {
            when (axis) {
                currentController.mapping.axisLeftX -> direction.x = 0f
                currentController.mapping.axisLeftY -> direction.y = 0f
            }
            return false
        }
        when (axis) {
            currentController.mapping.axisLeftX -> direction.x = value
            currentController.mapping.axisLeftY -> direction.y = -value //y-coordinates of controller are inverted
            else -> return false
        }
        return true
    }
}