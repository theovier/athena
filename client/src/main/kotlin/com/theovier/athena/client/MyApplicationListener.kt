package com.theovier.athena.client

import ktx.app.KtxApplicationAdapter

class MyApplicationListener : KtxApplicationAdapter {
    // Implementation of all ApplicationListener methods is optional. Override the ones you need.

    override fun create() {
        // Load the assets...
    }
    override fun render() {
        // ...and render your game.
    }
}