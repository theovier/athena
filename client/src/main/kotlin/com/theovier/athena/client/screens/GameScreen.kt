package com.theovier.athena.client.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.FitViewport
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.listeners.PhysicsListener
import com.theovier.athena.client.ecs.listeners.ProjectileCollisionListener
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.ecs.systems.*
import com.theovier.athena.client.math.xy
import ktx.app.KtxScreen
import ktx.ashley.allOf
import ktx.ashley.entity
import ktx.ashley.with
import ktx.box2d.body
import ktx.box2d.box
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class GameScreen : KtxScreen {
    private val steadyReferenceCamera = OrthographicCamera()
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(38f, 23f, camera) //width and height in units, 16:10
    private val engine = PooledEngine()
    private val map = Prefab.instantiate("map")
    private val player = Prefab.instantiate("player").apply {
        skeletalAnimation.build()
    }
    private val playersCrosshair = Prefab.instantiate("crosshair")
    private val world = World(Vector2.Zero, true)
    private val batch = SpriteBatch()


    init {
        initEntities()
        initSystems()
        initListeners()
        positionCamera()
    }

    private fun initEntities() {
        initSkeletonDemo()
        initPlayer()
        engine.addEntity(map)
        engine.addEntity(playersCrosshair)
    }

    private fun initPlayer() {
        val bodyComponent = Physics().apply {
            body = world.body(BodyDef.BodyType.DynamicBody) {
                box(width = 1f, height = 2f)
                position.set(player.transform.position.xy)
            }
        }
        player.add(bodyComponent)
        engine.addEntity(player)
    }

    private fun initSkeletonDemo() {
        //from prefab
//        val dummy = Prefab.instantiate("dummy") {
//            with(spineAnimation) {
//                build()
//            }
//        }
//        engine.addEntity(dummy)

        //without prefab
        engine.apply {
            entity {
                with<Transform>() {
                    size.set(1f, 2f)
                }
                with<SpineAnimation> {
                    pathToAtlasFile = "sprites/characters/dummy/dummy.atlas"
                    pathToSkeletonFile = "sprites/characters/dummy/dummy.json"
                }.build()
                with<Physics> {
                    body = world.body(BodyDef.BodyType.StaticBody) {
                        position.set(16f, 13f)
                        box(width = 1f, height = 2f)
                    }
                }
            }
        }
    }

    private fun initSystems() {
        engine.apply {
            addSystem(PhysicsSystem(world))
            addSystem(SpineAnimationSystem())
            addSystem(RenderingMetaSystem(camera, batch)
                .apply {
                    addSubsystem(BackgroundRenderingSystem(camera))
                    addSubsystem(SpriteRenderingSystem(batch))
                    addSubsystem(ParticleSystem(batch))
                    addSubsystem(SpineRenderingSystem(batch))
                })
            addSystem(CameraMovementSystem(steadyReferenceCamera))
            addSystem(MovementSystem())
            addSystem(PhysicMovementSystem())
            addSystem(PlayerMovementSystem())
            addSystem(PlayerAimSystem())
            addSystem(CrosshairPlacementSystem(player.aim))
            addSystem(PlayerAttackSystem(world)) //todo think of a different way (= not passing in the world) to let the attack system spawn physic entities
            addSystem(CameraShakeSystem(steadyReferenceCamera, camera))
            addSystem(LifetimeSystem())
            addSystem(PhysicsDebugSystem(world, camera))
        }
    }

    private fun initListeners() {
        engine.addEntityListener(allOf(Physics::class).get(), PhysicsListener())
        world.setContactListener(ProjectileCollisionListener(engine))
    }

    private fun positionCamera() {
        steadyReferenceCamera.position.set(playersCrosshair.transform.position)
    }

    override fun render(delta: Float) {
        engine.update(delta)
        viewport.apply()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        viewport.update(width, height)
    }

    override fun dispose() {
        world.dispose()
        batch.dispose()
        log.debug { "'${engine.entities.size()}' entities in engine" }
    }
}