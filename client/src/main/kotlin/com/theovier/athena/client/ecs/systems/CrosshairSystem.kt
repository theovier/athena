package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.inputs.XboxInputAdapter
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.math.compareTo
import ktx.math.minus
import ktx.math.plus
import ktx.math.times
import mu.KotlinLogging

private val log = KotlinLogging.logger {}
class CrosshairSystem(private val player: Entity) : IteratingSystem(allOf(Crosshair::class, Movement::class).get()) {

    private lateinit var currentController: Controller

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        currentController = Controllers.getCurrent()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (entities.size() > 1) {
            log.error { "More than 1 entity with <Crosshair> component detected." }
        }
        val xAxisValueRaw = currentController.getAxis(XboxInputAdapter.AXIS_RIGHT_X)
        val yAxisValueRaw = -currentController.getAxis(XboxInputAdapter.AXIS_RIGHT_Y)
        var stickInput = Vector2(xAxisValueRaw, yAxisValueRaw)
        if (stickInput.len() < XboxInputAdapter.LOOK_DEAD_ZONE) {
            stickInput = Vector2.Zero
        }
        val crosshairMovement = entity.movement
        //todo enforce max range restriction
        crosshairMovement.direction = stickInput
    }
}