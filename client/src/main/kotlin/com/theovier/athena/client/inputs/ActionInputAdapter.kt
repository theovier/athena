package com.theovier.athena.client.inputs

import ktx.app.KtxInputAdapter

interface ActionInputAdapter : KtxInputAdapter {
    fun subscribe(listener: ActionInputListener)
    fun unsubscribe(listener: ActionInputListener)
}