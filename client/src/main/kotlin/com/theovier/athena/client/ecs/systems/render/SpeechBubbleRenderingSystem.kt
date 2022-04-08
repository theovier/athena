package com.theovier.athena.client.ecs.systems.render

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture as Texture
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20.GL_TEXTURE0
import com.badlogic.gdx.graphics.GL20.GL_TEXTURE1
import com.badlogic.gdx.graphics.GLTexture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.render.*
import ktx.ashley.allOf
import ktx.assets.async.AssetStorage
import ktx.graphics.use
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SpeechBubbleRenderingSystem(private val camera: Camera) : IteratingSystem(allOf(SpeechBubble::class, WorldSpaceUI::class, Transform::class).get()),
    KoinComponent {
    private val log = KotlinLogging.logger {}
    private val assets: AssetStorage by inject()
    private val noiseTexture: Texture = assets["sprites/displacement.png"]
    private val batch = SpriteBatch().apply {
        shader = assets["shaders/doodle.frag"]
        log.debug { shader.log }
    }
    private var elapsedTime = 0f

    override fun update(deltaTime: Float) {
        elapsedTime += deltaTime

        noiseTexture.bind()

        batch.use(camera) {
            batch.shader.apply {
                //setUniformf("u_noise_scale", 0.1f)
                //setUniform2fv("u_noise_scroll_velocity", floatArrayOf(.006f, .007f), 0, 2)
                //setUniformf("u_distortion", 0.04f)
                setUniformf("u_time", elapsedTime)
//                setUniformf("u_speed", 0.5f)
//                setUniformf("u_intensity", 0.025f)
//                setUniformi("u_noiseTexture", 1)
            }
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val renderer = entity.renderer
        val sprite = renderer.sprite
        sprite.texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        sprite.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        SpriteRenderingSystem.renderEntity(entity, batch)
    }
}