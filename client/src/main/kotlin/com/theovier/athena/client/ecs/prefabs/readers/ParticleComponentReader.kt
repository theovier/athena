package com.theovier.athena.client.ecs.prefabs.readers

import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.components.Particle

class ParticleComponentReader : ComponentReader {
    override fun read(componentJSON: JsonValue): Particle {
        val component = Particle()
        if (componentJSON.has(EFFECT)) {
            val effectJSON = componentJSON.get(EFFECT)
            val effect = readParticleEffect(effectJSON)
            component.effect = effect
        }
        if (componentJSON.has(OFFSET)) {
            val offsetJSON = componentJSON.get(OFFSET)
            val offset = readVector2(offsetJSON)
            component.offset.set(offset)
        }
        return component
    }

    private fun readParticleEffect(node: JsonValue): ParticleEffect {
        val settingsFilePath = node.getString("settings")
        val imageFolderPath = node.getString("images")

        return ParticleEffect().apply {
            //todo load from asset manager (maybe inject an asset manager into componentReader?)
            //load(Gdx.files.local(settingsFilePath), Gdx.files.local(imageFolderPath))
        }
    }

    companion object {
        const val EFFECT = "effect"
        const val OFFSET = "offset"
    }
}