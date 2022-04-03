package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.AthenaGame
import com.theovier.athena.client.ecs.components.render.SpriteRenderer
import com.theovier.athena.client.ecs.components.render.SpriteRenderer.Companion.COLOR_HEX_DEFAULT
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool
import ktx.assets.async.AssetStorage

class SpriteComponentLoader(private val assets: AssetStorage) : ComponentLoader {

    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): SpriteRenderer {
        val component = SpriteRenderer()
        val texturePath = componentJSON.getString("texture")
        val texture: Texture = assets.loadSync(texturePath)
        component.sprite.setRegion(texture)
        component.sprite.setSize(
            texture.width * AthenaGame.UNIT_SCALE,
            texture.height * AthenaGame.UNIT_SCALE
        )
        if (componentJSON.has(OFFSET)) {
            val offsetJSON = componentJSON.get(OFFSET)
            val offset = ComponentLoader.readVector2(offsetJSON)
            component.offset.set(offset)
        }
        if (componentJSON.has(ORIGIN)) {
            val originJson = componentJSON.get(ORIGIN)
            val origin = ComponentLoader.readVector2(originJson)
            component.sprite.setOrigin(origin.x, origin.y)
        } else {
            component.sprite.setOriginCenter()
        }
        if (componentJSON.has(COLOR)) {
            val colorJson = componentJSON.get(COLOR)
            val hex = colorJson.getString("hex", COLOR_HEX_DEFAULT)
            component.sprite.color = Color.valueOf(hex)
        }
        return component
    }

    companion object {
        private const val OFFSET = "offset"
        private const val ORIGIN = "origin"
        private const val COLOR = "color"
    }
}