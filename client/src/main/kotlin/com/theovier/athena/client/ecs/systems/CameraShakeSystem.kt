package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.TimeUtils
import com.theovier.athena.client.ecs.components.Trauma
import com.theovier.athena.client.ecs.components.trauma
import com.theovier.athena.client.math.PerlinNoiseGenerator
import ktx.ashley.allOf
import ktx.math.plus
import mu.KotlinLogging

/* mutates the shakyCamera argument */
class CameraShakeSystem(private val camera: OrthographicCamera,
                        private val referenceCamera: OrthographicCamera) : IteratingSystem(allOf(Trauma::class).get()) {

    private val log = KotlinLogging.logger {}
    private val startTime = TimeUtils.millis()
    private val seed = 1
    private val noiseGeneratorX = PerlinNoiseGenerator(seed)
    private val noiseGeneratorY = PerlinNoiseGenerator(seed + 1)
    private val noiseGeneratorAngle = PerlinNoiseGenerator(seed + 2)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (entities.size() > 1) {
            log.error { "More than 1 entity with <Trauma> component detected. Stopping processing." }
            this.setProcessing(false)
        }
        val component = entity.trauma
        val trauma = component.trauma
        val shake = trauma * trauma
        val timePassed = TimeUtils.timeSinceMillis(startTime).toDouble()
        resetShakyCamera()
        shakeCameraPosition(timePassed, shake, component.maxTranslationalOffset)
        shakeCameraAngle(timePassed, shake, component.maxRotationalOffset)
        reduceTrauma(component, deltaTime)
    }

    private fun resetShakyCamera() {
        camera.up.set(referenceCamera.up)
    }

    private fun shakeCameraPosition(timePassed: Double, shake: Float, maxTranslationalOffset: Float) {
        val noise = Vector2(noiseGeneratorX.perlinWithNegative(timePassed), noiseGeneratorY.perlinWithNegative(timePassed))
        val offset = Vector3(maxTranslationalOffset * shake * noise.x, maxTranslationalOffset * shake * noise.y, 0f)
        val targetPos = referenceCamera.position + offset
        camera.position.set(targetPos)
    }

    private fun shakeCameraAngle(timePassed: Double, shake: Float, maxRotationalOffset: Float) {
        val noise = noiseGeneratorAngle.perlinWithNegative(timePassed)
        val offsetAngle = maxRotationalOffset * shake * noise
        camera.rotate(offsetAngle)
    }

    private fun reduceTrauma(component: Trauma, deltaTime: Float) {
        if (component.trauma > 0) {
            component.trauma -= component.reductionFactor * deltaTime
        }
    }
}