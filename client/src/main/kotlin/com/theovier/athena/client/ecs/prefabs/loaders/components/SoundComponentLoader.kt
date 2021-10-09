package com.theovier.athena.client.ecs.prefabs.loaders.components

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.utils.JsonValue
import com.theovier.athena.client.ecs.prefabs.loaders.DependencyPool
import com.theovier.athena.client.misc.audio.SoundEffect
import ktx.assets.async.AssetStorage
import com.theovier.athena.client.ecs.components.Sound as SoundComponent

class SoundComponentLoader(private val assets: AssetStorage) : ComponentLoader {

    override fun load(componentJSON: JsonValue, dependencyPool: DependencyPool): SoundComponent {
        val soundEffect: SoundEffect = if(componentJSON.has(CHOICE)) {
            val choicesJSON = componentJSON.get(CHOICE)
            val choices = readChoices(choicesJSON)
            choices.random()
        } else {
            readSoundEffect(componentJSON)
        }
        return SoundComponent().apply {
            effect = soundEffect
        }
    }

    private fun readChoices(choiceJSON: JsonValue): List<SoundEffect> {
        val choices = ArrayList<SoundEffect>()
        val iterator = choiceJSON.iterator()
        while (iterator.hasNext()) {
            val choice = iterator.next()
            val effect = readSoundEffect(choice)
            choices.add(effect)
        }
        return choices
    }

    private fun readSoundEffect(json: JsonValue): SoundEffect {
        val file = json.getString(FILE)
        val volume = json.getFloat(VOLUME, SoundComponent.DEFAULT_VOLUME)
        val sound: Sound = assets.loadSync(file)
        return SoundEffect(sound, volume)
    }

    companion object {
        const val CHOICE = "choice"
        const val FILE = "file"
        const val VOLUME = "volume"
    }
}