package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.AthenaGame
import com.theovier.athena.client.ecs.components.Particle
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool

class ParticleComponentLoader : ComponentLoader {

    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): Particle {
        val component = Particle()
        if (componentJSON.has(EFFECT)) {
            val effectJSON = componentJSON.get(EFFECT)
            val effect = readParticleEffect(effectJSON)
            component.effect = effect
        }
        if (componentJSON.has(OFFSET)) {
            val offsetJSON = componentJSON.get(OFFSET)
            val offset = ComponentLoader.readVector2(offsetJSON)
            component.offset.set(offset)
        }
        component.effect.scaleEffect(AthenaGame.UNIT_SCALE)
        if (componentJSON.getBoolean(AUTOSTART, true)) {
            component.effect.start()
        }
        return component
    }

    private fun readParticleEffect(node: JsonValue): ParticleEffect {
        val settingsFilePath = node.getString("settings")
        val imageFolderPath = node.getString("images")

        return ParticleEffect().apply {
            load(Gdx.files.local(settingsFilePath), Gdx.files.local(imageFolderPath))
        }
    }

    companion object {
        const val EFFECT = "effect"
        const val OFFSET = "offset"
        const val AUTOSTART = "autostart"
    }
}