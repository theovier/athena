package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import com.theovier.athena.client.ecs.prefabs.serializers.SpriteSerializer
import com.theovier.athena.client.ecs.prefabs.serializers.Vector2Serializer
import kotlinx.serialization.Serializable
import ktx.ashley.get
import ktx.ashley.mapperFor
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
class SpriteRenderer : Component, Poolable {

    @XmlElement(true)
    @XmlSerialName("Sprite", "", "")
    @Serializable(with = SpriteSerializer::class)
    val sprite = Sprite()

    @XmlElement(true)
    @XmlSerialName("Offset", "", "")
    @Serializable(with = Vector2Serializer::class)
    val offset = Vector2()

    override fun reset() {
        sprite.texture = null
        sprite.setColor(1f, 1f, 1f, 1f)
        offset.set(0f, 0f)
    }

    companion object {
        val MAPPER = mapperFor<SpriteRenderer>()
    }
}

val Entity.renderer: SpriteRenderer
    get() = this[SpriteRenderer.MAPPER] ?: throw GdxRuntimeException("Renderer for entity '$this' is null")