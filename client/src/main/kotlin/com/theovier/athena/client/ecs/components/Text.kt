package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.GdxRuntimeException
import ktx.ashley.get
import ktx.ashley.mapperFor

class Text : Component {
    var text = ""
    var color = Color.WHITE

    companion object {
        val MAPPER = mapperFor<Text>()
    }
}

val Entity.text: Text
    get() = this[Text.MAPPER] ?: throw GdxRuntimeException("Text for entity '$this' is null")