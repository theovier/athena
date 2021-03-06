package com.theovier.athena.client.misc.spine

import com.badlogic.gdx.math.Vector2
import com.esotericsoftware.spine.Skeleton

fun Skeleton.faceDirection(direction: Vector2) {
    if (direction.x < 0) {
        forceToFaceLeft()
    } else {
        forceToFaceRight()
    }
}

fun Skeleton.forceToFaceLeft() {
    if(scaleX >= 0) {
        flipX()
    }
}

fun Skeleton.forceToFaceRight() {
    if(scaleX < 0) {
        flipX()
    }
}

fun Skeleton.flipX() {
    scaleX *= -1
}

fun Skeleton.isMirrored(): Boolean = isFacingLeft()

fun Skeleton.isFacingRight(): Boolean {
    return this.scaleX >= 0
}

fun Skeleton.isFacingLeft(): Boolean {
    return this.scaleX < 0
}