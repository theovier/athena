package com.theovier.athena.client.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.viewport.FitViewport
import com.esotericsoftware.spine.*
import com.theovier.athena.client.AthenaGame
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.ecs.systems.*
import ktx.app.KtxScreen
import ktx.graphics.use
import mu.KotlinLogging


private val log = KotlinLogging.logger {}

class GameScreen(private val game: AthenaGame) : KtxScreen {
    private val steadyReferenceCamera = OrthographicCamera()
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(38f, 23f, camera) //width and height in units, 16:10
    private val engine = PooledEngine()
    private val map = Prefab.instantiate("map")
    private val player = Prefab.instantiate("player")
    private val playersCrosshair = Prefab.instantiate("crosshair")

    //spine test
    private val atlas = TextureAtlas(Gdx.files.internal("sprites/characters/dummy/dummy.atlas"))
    private val skeletonLoader = SkeletonJson(atlas)
    private val skeletonData = skeletonLoader.readSkeletonData(Gdx.files.internal("sprites/characters/dummy/dummy.json"))
    private val skeleton = Skeleton(skeletonData)
    private val animationStateData = AnimationStateData(skeletonData)
    private val animationState = AnimationState(animationStateData)
    private val skeletonRenderer = SkeletonRenderer()

    init {
        initSkeletonDemo()
        initEntities()
        initSystems()
        positionCamera()
    }

    private fun initSkeletonDemo() {
        //skeletonRenderer.setPremultipliedAlpha(true)
        skeleton.setScale(1/150f, 1/150f)
        skeleton.setPosition(12f, 6f)
        animationState.setAnimation(0, "idle", true)
    }

    private fun initEntities() {
        engine.addEntity(map)
        engine.addEntity(player)
        engine.addEntity(playersCrosshair)
    }

    private fun initSystems() {
        engine.apply {
            addSystem(BackgroundRenderSystem(camera))
            addSystem(RenderingSystem(game.batch))
            addSystem(ParticleSystem(game.batch))
            addSystem(CameraMovementSystem(steadyReferenceCamera))
            addSystem(MovementSystem())
            addSystem(PlayerMovementSystem())
            addSystem(PlayerAimSystem())
            addSystem(CrosshairPlacementSystem(player.aim))
            addSystem(PlayerAttackSystem())
            addSystem(CameraShakeSystem(steadyReferenceCamera, camera))
            addSystem(LifetimeSystem())
        }
    }

    private fun positionCamera() {
        steadyReferenceCamera.position.set(playersCrosshair.transform.position)
    }

    override fun render(delta: Float) {

        animationState.update(delta)
        animationState.apply(skeleton)
        skeleton.updateWorldTransform()

        game.batch.use(camera) {
            engine.update(delta)
            skeletonRenderer.draw(game.batch, skeleton)
        }
        viewport.apply()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        viewport.update(width, height)
    }

    override fun dispose() {
        log.debug { "'${engine.entities.size()}' entities in engine" }
    }
}