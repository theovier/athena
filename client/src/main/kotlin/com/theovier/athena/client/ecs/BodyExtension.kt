package com.theovier.athena.client.ecs

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Body

var Body.isEntity: Boolean
    get() = this.userData != null && this.userData is Entity
    set(value) = Unit