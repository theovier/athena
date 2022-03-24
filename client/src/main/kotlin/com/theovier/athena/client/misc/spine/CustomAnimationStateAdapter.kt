package com.theovier.athena.client.misc.spine

import com.esotericsoftware.spine.AnimationState
import com.esotericsoftware.spine.AnimationState.AnimationStateListener
import com.esotericsoftware.spine.Event

interface CustomAnimationStateAdapter : AnimationStateListener {
    override fun start(entry: AnimationState.TrackEntry) = Unit
    override fun interrupt(entry: AnimationState.TrackEntry) = Unit
    override fun end(entry: AnimationState.TrackEntry) = Unit
    override fun dispose(entry: AnimationState.TrackEntry) = Unit
    override fun complete(entry: AnimationState.TrackEntry) = Unit
    override fun event(entry: AnimationState.TrackEntry, event: Event) = Unit
}