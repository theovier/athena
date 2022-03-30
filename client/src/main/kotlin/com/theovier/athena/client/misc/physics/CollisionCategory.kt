package com.theovier.athena.client.misc.physics

class CollisionCategory {
    companion object {
        const val PLAYER: Short = 1
        const val ENEMY: Short = 2
        const val BULLET: Short = 4
        const val DOODAD: Short = 8
        const val WALL: Short = 16
    }
}