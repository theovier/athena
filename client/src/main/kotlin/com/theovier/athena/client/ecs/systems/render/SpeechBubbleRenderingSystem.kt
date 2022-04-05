package com.theovier.athena.client.ecs.systems.render

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.components.render.renderer
import ktx.ashley.allOf
import ktx.graphics.use
import mu.KotlinLogging


class SpeechBubbleRenderingSystem(private val camera: Camera, private val demoShader: ShaderProgram, private val noiseTexture: Texture) :
    IteratingSystem(allOf(Demo::class, Transform::class).get()) {

    private val myBatch = SpriteBatch().apply {
        shader = demoShader
    }

    private var time = 0f

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val position = transform.position
        val size = transform.size
        val renderer = entity.renderer

        time += deltaTime


        renderer.sprite.texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        renderer.sprite.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        myBatch.use(camera) {
            noiseTexture.bind()
            demoShader.setUniformf("u_noise_scale", 0.1f)
            demoShader.setUniform2fv("u_noise_scroll_velocity", floatArrayOf(.006f, .007f), 0, 2)
            demoShader.setUniformf("u_distortion", 0.04f)
            demoShader.setUniformf("u_time", time)


            renderer.sprite.run {
                setScale(1 / width * transform.size.x, 1 / height * transform.size.y)
                setPosition(transform.position.x, transform.position.y)
                rotation = transform.rotationDegrees

                //put sprite to bottom left corner
                x -= originX * (1f - scaleX)
                y -= originY * (1f - scaleY)

                //center sprite horizontally
                x += (transform.size.x - width * scaleX) * 0.5f

                draw(myBatch)
            }

        }
    }
}