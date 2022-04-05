package com.theovier.athena.client.ecs.systems.render

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.render.WorldSpaceUI
import com.theovier.athena.client.ecs.components.render.renderer
import ktx.ashley.allOf
import ktx.graphics.use

//todo: look into rendering to buffers to avoid having a different batch for each system that uses a shader
class SpeechBubbleRenderingSystem(private val camera: Camera, private val wobbleShader: ShaderProgram, private val noiseTexture: Texture) :
    IteratingSystem(allOf(SpeechBubble::class, WorldSpaceUI::class, Transform::class).get()) {
    private var elapsedTime = 0f
    private val batch = SpriteBatch().apply {
        shader = wobbleShader
    }

    override fun update(deltaTime: Float) {
        elapsedTime += deltaTime
        batch.use(camera) {
            batch.shader.apply {
                setUniformf("u_noise_scale", 0.1f)
                setUniform2fv("u_noise_scroll_velocity", floatArrayOf(.006f, .007f), 0, 2)
                setUniformf("u_distortion", 0.04f)
                setUniformf("u_time", elapsedTime)
            }
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val renderer = entity.renderer
        renderer.sprite.texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        renderer.sprite.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        noiseTexture.bind()
        SpriteRenderingSystem.renderEntity(entity, batch)
    }
}