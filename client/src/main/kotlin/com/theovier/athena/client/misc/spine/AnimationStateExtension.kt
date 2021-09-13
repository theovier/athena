package com.theovier.athena.client.misc.spine

import com.esotericsoftware.spine.AnimationState

fun AnimationState.playAnimationIfNotAlreadyPlaying(trackIndex: Int = 0, name: String, isLooping: Boolean = true) {
    val currentTrackEntry = getCurrent(trackIndex) ?: return
    val currentAnimation = currentTrackEntry.animation
    if (currentAnimation.name != name) {
        setAnimation(trackIndex, name, isLooping)
    }
}