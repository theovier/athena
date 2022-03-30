package com.theovier.athena.client.misc.physics

class CollisionCategory {
    companion object {
        const val PLAYER: Short = 1
        const val ENEMY: Short = 2
        const val BULLET: Short = 4
        const val COLLIDABLE: Short = 8
        const val NON_COLLIDABLE: Short = 16
    }
}