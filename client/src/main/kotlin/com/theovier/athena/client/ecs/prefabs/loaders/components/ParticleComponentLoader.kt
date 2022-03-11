package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.JsonValue
import com.talosvfx.talos.runtime.ParticleEffectDescriptor
import com.talosvfx.talos.runtime.ParticleEffectInstance
import com.theovier.athena.client.ecs.components.Particle
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool
import ktx.assets.async.AssetStorage
import ktx.assets.async.toIdentifier


class ParticleComponentLoader(private val assets: AssetStorage) : ComponentLoader {

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
        return component
    }

    private fun readParticleEffect(node: JsonValue): ParticleEffectInstance {
        val settingsFilePath = node.getString("settings")
        val atlasPath = node.getString("atlas")
        val atlas = readAtlas(atlasPath)
        val settings = Gdx.files.local(settingsFilePath)
        val effectDescriptor = ParticleEffectDescriptor(settings, atlas)
        return effectDescriptor.createEffectInstance()
    }

    private fun readAtlas(path: String): TextureAtlas {
        val descriptor = AssetDescriptor(path, TextureAtlas::class.java)
        return assets.loadSync(descriptor.toIdentifier())
    }

    companion object {
        const val EFFECT = "effect"
        const val OFFSET = "offset"
    }
}