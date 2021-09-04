package com.theovier.athena.client.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.inputs.XboxInputAdapter
import ktx.ashley.allOf
import ktx.box2d.body
import ktx.box2d.box

class PlayerAttackSystem(private val world: World) : XboxInputAdapter, IteratingSystem(allOf(Player::class, Aim::class, SpineAnimation::class).get()) {
    private lateinit var currentController: Controller
    private var wantsToFire = false

    //todo use weapons and dedicated components instead
    private var timeBetweenShots = 0.2f
    private var canNextFireInSeconds = 0f

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        currentController = Controllers.getCurrent()
        currentController.addListener(this)
    }

    override fun removedFromEngine(engine: Engine?) {
        super.removedFromEngine(engine)
        currentController.removeListener(this)
    }

    override fun processEntity(player: Entity, deltaTime: Float) {
        processInput()
        if (wantsToFire && canFire()) {
            fire(player)
        }
        if (canNextFireInSeconds > 0) {
            canNextFireInSeconds -= deltaTime
        }
    }

    private fun processInput() {
        val axisValueRaw = currentController.getAxis(XboxInputAdapter.AXIS_TRIGGER_RIGHT)
        wantsToFire = axisValueRaw >= XboxInputAdapter.FIRE_DEAD_ZONE
    }

    private fun canFire(): Boolean {
        return canNextFireInSeconds <= 0
    }

    private fun fire(shooter: Entity) {
        val headBone = shooter.skeletalAnimation.skeleton.findBone("head")
        val origin = Vector2(headBone.worldX, headBone.worldY)
        spawnBullet(origin, shooter.aim.direction)
        canNextFireInSeconds = timeBetweenShots
        wantsToFire = false
    }

    private fun spawnBullet(origin: Vector2, direction: Vector2) {
        val bullet = Prefab.instantiate(BULLET_ENTITY) {
            with(movement) {
                this.direction = direction
            }
        }

        //testwise add the physic components manually until they can be persisted in prefabs
        val bodyComponent = Physics().apply {
            body = world.body(BodyDef.BodyType.DynamicBody) {
                box(width = bullet.transform.size.x, height = bullet.transform.size.y)
                position.set(origin.x, origin.y + 2f)
                angle = direction.angleRad()
            }
            body.isBullet = true
        }
        bullet.add(bodyComponent)

        engine.addEntity(bullet)
    }

    companion object {
        const val BULLET_ENTITY = "bullet"
    }
}