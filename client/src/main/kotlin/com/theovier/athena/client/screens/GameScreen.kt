package com.theovier.athena.client.screens

import com.badlogic.ashley.core.Family
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.FitViewport
import com.theovier.athena.client.AthenaGame
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.listeners.PhysicsListener
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.ecs.systems.*
import com.theovier.athena.client.math.xy
import ktx.app.KtxScreen
import ktx.ashley.allOf
import ktx.ashley.entity
import ktx.ashley.with
import ktx.box2d.body
import ktx.box2d.box
import ktx.graphics.use
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class GameScreen(private val game: AthenaGame) : KtxScreen {
    private val steadyReferenceCamera = OrthographicCamera()
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(38f, 23f, camera) //width and height in units, 16:10
    private val engine = PooledEngine()
    private val debugEngine = PooledEngine()
    private val map = Prefab.instantiate("map")
    private val player = Prefab.instantiate("player").apply {
        skeletalAnimation.build()
    }
    private val playersCrosshair = Prefab.instantiate("crosshair")
    private val world = World(Vector2.Zero, true)

    init {
        initEntities()
        initSystems()
        initDebugSystems()
        addEntityListeners()
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
//            with(skeletalAnimation) {
//                build()
//            }
//        }
//        engine.addEntity(dummy)

        //without prefab
        engine.apply {
            entity {
                with<Transform>()
                with<SkeletalAnimation> {
                    pathToAtlasFile = "sprites/characters/dummy/dummy.atlas"
                    pathToSkeletonFile = "sprites/characters/dummy/dummy.json"
                }.build()
                with<Physics> {
                    body = world.body(BodyDef.BodyType.StaticBody) {
                        box(width = 1f, height = 2f)
                        position.set(16f, 13f)
                    }
                }
            }
        }
    }

    private fun initSystems() {
        engine.apply {
            addSystem(BackgroundRenderingSystem(camera))
            addSystem(RenderingSystem(game.batch))
            addSystem(ParticleSystem(game.batch))
            addSystem(SkeletalAnimationSystem())
            addSystem(SkeletonRenderingSystem(game.batch))
            addSystem(CameraMovementSystem(steadyReferenceCamera))
            addSystem(MovementSystem())
            addSystem(PlayerMovementSystem())
            addSystem(PlayerAimSystem())
            addSystem(CrosshairPlacementSystem(player.aim))
            addSystem(PlayerAttackSystem(world)) //todo think of a different way (= not passing in the world) to let the attack system spawn physic entities
            addSystem(CameraShakeSystem(steadyReferenceCamera, camera))
            addSystem(LifetimeSystem())
            addSystem(PhysicsSystem(world))
        }
    }

    /* systems that need to be called after the batch's end command, see https://stackoverflow.com/a/32791208/6516194 */
    private fun initDebugSystems() {
        debugEngine.apply {
            addSystem(PhysicsDebugSystem(world, camera))
        }
    }

    private fun addEntityListeners() {
        engine.addEntityListener(allOf(Physics::class).get(), PhysicsListener())
    }

    private fun positionCamera() {
        steadyReferenceCamera.position.set(playersCrosshair.transform.position)
    }

    override fun render(delta: Float) {
        game.batch.use(camera) {
            engine.update(delta)
        }
        debugEngine.update(delta)
        viewport.apply()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        viewport.update(width, height)
    }

    override fun dispose() {
        world.dispose()
        log.debug { "'${engine.entities.size()}' entities in engine" }
    }
}