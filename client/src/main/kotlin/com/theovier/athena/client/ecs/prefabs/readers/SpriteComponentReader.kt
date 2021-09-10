package com.theovier.athena.client.ecs.prefabs.readers

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.AthenaGame
import com.theovier.athena.client.ecs.components.SpriteRenderer
import ktx.assets.async.AssetStorage

class SpriteComponentReader(private val assets: AssetStorage) : ComponentReader {

    override fun read(componentJSON: JsonValue): Component {
        val component = SpriteRenderer()
        val texturePath = componentJSON.getString("texture")
        val texture: Texture = assets[texturePath]
        component.sprite.setRegion(texture)
        component.sprite.setSize(
            texture.width * AthenaGame.UNIT_SCALE,
            texture.height * AthenaGame.UNIT_SCALE
        )
        if (componentJSON.has(OFFSET)) {
            val offsetJSON = componentJSON.get(OFFSET)
            val offset = ComponentReader.readVector2(offsetJSON)
            component.offset.set(offset)
        }
        if (componentJSON.has(ORIGIN)) {
            val originJson = componentJSON.get(ORIGIN)
            val origin = ComponentReader.readVector2(originJson)
            component.sprite.setOrigin(origin.x, origin.y)
        } else {
            component.sprite.setOriginCenter()
        }
        return component
    }

    companion object {
        private const val OFFSET = "offset"
        private const val ORIGIN = "origin"
    }
}