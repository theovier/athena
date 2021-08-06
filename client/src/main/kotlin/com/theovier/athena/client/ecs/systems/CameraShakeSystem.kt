package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.viewport.Viewport
import com.theovier.athena.client.math.PerlinNoiseGenerator
import ktx.app.KtxInputAdapter
import ktx.math.plus

class CameraShakeSystem(viewport: Viewport) : KtxInputAdapter, EntitySystem() {
    var trauma = 0.0f// from 0 to 1

    private val baseCamera = viewport.camera as OrthographicCamera
    private val shakyCamera = OrthographicCamera()

    private val startTime = TimeUtils.millis()

    private val seed = 1
    private val noiseGeneratorX = PerlinNoiseGenerator(seed)
    private val noiseGeneratorY = PerlinNoiseGenerator(seed + 1)
    private val noiseGeneratorAngle = PerlinNoiseGenerator(seed + 2)

    companion object {
        private const val MIN_TRAUMA = 0f
        private const val MAX_TRAUMA = 1f
        private const val TRAUMA_ADDED_ON_HIT = 0.5f
        private const val TRAUMA_REDUCE_FACTOR = 0.6f
        private const val MAX_TRANSLATIONAL_OFFSET = 0.5f
        private const val MAX_ROTATIONAL_OFFSET = 5
    }

    init {
        viewport.camera = shakyCamera
    }

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        (Gdx.input.inputProcessor as InputMultiplexer).addProcessor(this)
    }

    override fun removedFromEngine(engine: Engine?) {
        super.removedFromEngine(engine)
        (Gdx.input.inputProcessor as InputMultiplexer).removeProcessor(this)
    }

    //https://www.youtube.com/watch?v=tu-Qe66AvtY
    override fun update(deltaTime: Float) {
        val shake = trauma * trauma
        val timePassed = TimeUtils.timeSinceMillis(startTime).toDouble()
        resetShakyCamera()
        shakeCameraPosition(timePassed, shake)
        shakeCameraAngle(timePassed, shake)
        reduceTrauma(deltaTime)
    }

    private fun resetShakyCamera() {
        shakyCamera.up.set(baseCamera.up)
    }

    private fun shakeCameraPosition(timePassed: Double, shake: Float) {
        val noise = Vector2(noiseGeneratorX.perlinWithNegative(timePassed), noiseGeneratorY.perlinWithNegative(timePassed))
        val offset = Vector3(MAX_TRANSLATIONAL_OFFSET * shake * noise.x, MAX_TRANSLATIONAL_OFFSET * shake * noise.y, 0f)
        val targetPos = baseCamera.position + offset
        shakyCamera.position.set(targetPos)
    }

    private fun shakeCameraAngle(timePassed: Double, shake: Float) {
        val noise = noiseGeneratorAngle.perlinWithNegative(timePassed)
        val offsetAngle = MAX_ROTATIONAL_OFFSET * shake * noise
        shakyCamera.rotate(offsetAngle)
    }

    private fun reduceTrauma(deltaTime: Float) {
        if (trauma > 0) {
            trauma -= TRAUMA_REDUCE_FACTOR * deltaTime
            trauma = MathUtils.clamp(trauma, MIN_TRAUMA, MAX_TRAUMA)
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.F -> {
                trauma = MathUtils.clamp(trauma + TRAUMA_ADDED_ON_HIT, MIN_TRAUMA, MAX_TRAUMA)
            }
            else -> return false
        }
        return true
    }
}