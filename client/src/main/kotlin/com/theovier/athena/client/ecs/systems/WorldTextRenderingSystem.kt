package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.theovier.athena.client.ecs.components.Text
import com.theovier.athena.client.ecs.components.Transform
import com.theovier.athena.client.ecs.components.text
import com.theovier.athena.client.ecs.components.transform
import ktx.ashley.allOf
import ktx.assets.async.AssetStorage
import ktx.freetype.generateFont
import ktx.math.times
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WorldTextRenderingSystem(private val batch: Batch) : IteratingSystem(allOf(Transform::class, Text::class).get()),
    KoinComponent {
    private val assets: AssetStorage by inject()
    private val font: BitmapFont

    init {
        val generator = assets.loadSync<FreeTypeFontGenerator>("fonts/open-sans.regular.ttf")
        font = generator.generateFont {
            size = FONT_SIZE
        }.apply {
            data.setScale(FONT_SCALE_MODIFIER, FONT_SCALE_MODIFIER)
            region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
            setUseIntegerPositions(false) //https://github.com/libgdx/libgdx/issues/3827
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = entity.transform.position
        val size = entity.transform.size * FONT_SCALE_MODIFIER
        val text = entity.text.text
        font.run {
            color = entity.text.color
            data.setScale(size.x, size.y)
            draw(batch, text, position.x, position.y)
        }
    }

    companion object {
        private const val FONT_SIZE = 62
        private const val FONT_SCALE_MODIFIER = 0.01f
    }
}