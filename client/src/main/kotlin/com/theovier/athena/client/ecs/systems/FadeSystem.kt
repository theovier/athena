package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.theovier.athena.client.ecs.components.*
import ktx.ashley.allOf

class FadeSystem : IteratingSystem(allOf(Fade::class, Timer::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val fade = entity.fade
        val timer = entity.timer

        timer.millisSinceStart += deltaTime
        if (timer.millisSinceStart >= fade.duration) {
            entity.remove(Timer::class.java)
        }

        val alpha = MathUtils.clamp(timer.millisSinceStart / fade.duration, 0.0f, 1.0f)
        fade.current = lerp(fade.start, fade.end, alpha)
    }

    private fun lerp(a: Float, b: Float, alpha: Float): Float {
        return a + alpha * (b - a)
    }
}