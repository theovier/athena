package com.theovier.athena.client.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

class Demo : Component {
    var origin = Vector2(0f, 0f) //todo own component? With Float maxDistance

    val velocity = Vector2(
        MathUtils.random(-5f, -10f),
        MathUtils.random(5f, 10f)
    )
    var standingStillThreshold = 0.5f
    var velocityTime = 0f
    val acceleration = -9.8f


}