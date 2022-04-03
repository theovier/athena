package com.theovier.athena.client.ecs.components.render

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.get
import ktx.ashley.mapperFor

class Text : Component, Poolable {
    var text = ""
    var color = Color(1.0f, 1.0f, 1.0f, 1.0f)

    override fun reset() {
        text = ""
        color = Color(1.0f, 1.0f, 1.0f, 1.0f)
    }

    companion object {
        val MAPPER = mapperFor<Text>()
    }
}

val Entity.text: Text
    get() = this[Text.MAPPER] ?: throw GdxRuntimeException("Text for entity '$this' is null")