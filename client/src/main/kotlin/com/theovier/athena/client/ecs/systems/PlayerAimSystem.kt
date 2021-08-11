package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.Vector2
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.inputs.XboxInputAdapter
import com.theovier.athena.client.math.xy
import ktx.ashley.allOf
import ktx.math.minus

//todo still feels horrible
//https://www.gamasutra.com/blogs/MarkVenturelli/20150817/251387/Everything_I_Learned_About_DualStick_Shooter_Controls.php
class PlayerAimSystem : IteratingSystem(allOf(Aim::class, Movement::class).get())  {
    private lateinit var currentController: Controller

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        currentController = Controllers.getCurrent()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
//        val xAxisValueRaw = currentController.getAxis(XboxInputAdapter.AXIS_RIGHT_X)
//        val yAxisValueRaw = -currentController.getAxis(XboxInputAdapter.AXIS_RIGHT_Y)
//        var stickInput = Vector2(xAxisValueRaw, yAxisValueRaw)
//        if (stickInput.len() < XboxInputAdapter.DEAD_ZONE) {
//            stickInput = Vector2.Zero
//        }
//        entity.aim.direction = stickInput.nor()
    }
}