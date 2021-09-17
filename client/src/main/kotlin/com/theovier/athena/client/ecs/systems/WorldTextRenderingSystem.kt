package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.theovier.athena.client.ecs.components.Text
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.text
import com.theovier.athena.client.ecs.components.transform
import ktx.ashley.allOf

class WorldTextRenderingSystem(private val batch: Batch) : IteratingSystem(allOf(Transform::class, Text::class).get()){
    private val font: BitmapFont

    init {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/open-sans.regular.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter().apply {
            size = 62
        }
        font = generator.generateFont(parameter).apply {
            data.setScale(0.01f, 0.01f)
            region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
            setUseIntegerPositions(false) //https://github.com/libgdx/libgdx/issues/3827
        }
        generator.dispose()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = entity.transform.position
        val text = entity.text.text
        font.color = entity.text.color
        font.draw(batch, text, position.x, position.y)
    }
}