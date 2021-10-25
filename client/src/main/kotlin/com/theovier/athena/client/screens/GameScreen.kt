package com.theovier.athena.client.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.theovier.athena.client.ecs.components.*
import com.theovier.athena.client.ecs.listeners.InvisibleListener
import com.theovier.athena.client.ecs.listeners.physics.PhysicsListener
import com.theovier.athena.client.ecs.listeners.physics.ProjectileCollisionListener
import com.theovier.athena.client.ecs.prefabs.Prefab
import com.theovier.athena.client.ecs.systems.*
import com.theovier.athena.client.ecs.systems.cleanup.CleanupHitMarkerSystem
import com.theovier.athena.client.ecs.systems.cleanup.CleanupSoundSystem
import com.theovier.athena.client.ecs.systems.damage.DamageIndicatorSystem
import com.theovier.athena.client.ecs.systems.damage.HapticDamageFeedbackSystem
import com.theovier.athena.client.ecs.systems.damage.HealthSystem
import com.theovier.athena.client.ecs.systems.movement.AccelerationSystem
import com.theovier.athena.client.ecs.systems.CameraMovementSystem
import com.theovier.athena.client.ecs.systems.movement.FrictionSystem
import com.theovier.athena.client.ecs.systems.movement.MovementSystem
import com.theovier.athena.client.ecs.systems.physics.PhysicMovementSystem
import com.theovier.athena.client.ecs.systems.physics.PhysicsDebugSystem
import com.theovier.athena.client.ecs.systems.physics.PhysicsSystem
import com.theovier.athena.client.ecs.systems.player.FacingSystem
import com.theovier.athena.client.ecs.systems.player.PlayerAimSystem
import com.theovier.athena.client.ecs.systems.player.PlayerAttackSystem
import com.theovier.athena.client.ecs.systems.player.PlayerMovementSystem
import com.theovier.athena.client.ecs.systems.render.*
import ktx.app.KtxScreen
import ktx.ashley.addComponent
import ktx.ashley.allOf
import ktx.ashley.entity
import ktx.ashley.with
import ktx.scene2d.*
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private val log = KotlinLogging.logger {}

class GameScreen(private val world: World) : KtxScreen, KoinComponent {
    private val steadyReferenceCamera = OrthographicCamera()
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(38f, 23f, camera) //width and height in units, 16:10
    private val engine: PooledEngine by inject()
    private val player = Prefab.instantiate("player")
    private val crosshair = Prefab.instantiate("crosshair")
    private val batch = SpriteBatch()

    //injected systems
    private val physicsSystem: PhysicsSystem by inject()
    private val spriteRenderingSystem = SpriteRenderingSystem(batch)

    //Debug UI
    private val uiCamera = OrthographicCamera()
    private val uiViewport = ScreenViewport(uiCamera)
    private val stage = Stage(uiViewport, batch)
    private lateinit var debugLabelFPS: Label
    private lateinit var debugLabelPlayerPosition: Label
    private lateinit var debugEntityCountLabel: Label

    init {
        initSingletonComponents()
        initSystems()
        initListeners()
        initEntities()
        initDebugUI()
        positionCamera()
    }

    private fun initSingletonComponents() {
        engine.apply {
            entity {
                with<Input>()
            }
        }
    }

    private fun initEntities() {
        Prefab.instantiate("map")
        val wall = Prefab.instantiate("wall")
        wall.add(Impact())


        Prefab.instantiate("dummy")
        Prefab.instantiate("dummy") {
            with(physics) {
                body.setTransform(Vector2(12f, 17f), 0f)
            }
        }
    }

    private fun initSystems() {
        engine.apply {
            addSystem(InputSystem())
            addSystem(physicsSystem)
            addSystem(SpineAnimationSystem())
            addSystem(ChildrenPositionSystem())
            addSystem(
                RenderingMetaSystem(camera, batch)
                .apply {
                    addSubsystem(BackgroundRenderingSystem(camera))
                    addSubsystem(spriteRenderingSystem)
                    addSubsystem(ParticleSystem(batch))
                    addSubsystem(SpineRenderingSystem(batch))
                    addSubsystem(ForegroundSpriteRenderingSystem(spriteRenderingSystem))
                    addSubsystem(WorldTextRenderingSystem(batch))
                })
            addSystem(CameraMovementSystem(camera, steadyReferenceCamera))
            addSystem(CameraShakeSystem(camera, steadyReferenceCamera))
            addSystem(AccelerationSystem())
            addSystem(FrictionSystem())
            addSystem(MovementSystem())
            addSystem(BulletShellEjectionSystem())
            addSystem(SpinningSystem())
            addSystem(PhysicMovementSystem())
            addSystem(PlayerMovementSystem())
            addSystem(HealthBarScalingSystem())
            addSystem(HealthBarToggleSystem())
            addSystem(FacingSystem())
            addSystem(DustTrailSpawnSystem())
            addSystem(PlayerAimSystem())
            addSystem(WeaponRotationSystem())
            addSystem(CrosshairPlacementSystem(player.aim))
            addSystem(PlayerAttackSystem())
            addSystem(LifetimeSystem())
            addSystem(DamageIndicatorSystem())
            addSystem(HapticDamageFeedbackSystem())
            addSystem(HealthSystem())
            addSystem(SoundSystem())
            addSystem(ImpactSpawnSystem())
            addSystem(CleanupHitMarkerSystem())
            addSystem(CleanupSoundSystem())
            //addSystem(PhysicsDebugSystem(world, camera))
            //addSystem(SpineDebugSystem(camera))
        }
    }

    private fun initListeners() {
        engine.addEntityListener(allOf(Physics::class).get(), PhysicsListener())
        engine.addEntityListener(allOf(Invisible::class).get(), InvisibleListener())
        world.setContactListener(ProjectileCollisionListener(engine))
    }

    private fun initDebugUI() {
        stage.actors {
            table {
                setFillParent(true)
                left().top()
                padLeft(100f)
                verticalGroup {
                    debugLabelFPS = label("FPS Counter")
                    debugLabelPlayerPosition = label("Player Position")
                    debugEntityCountLabel = label("Entity Counter")
                }
            }
        }
    }

    private fun positionCamera() {
        camera.position.set(crosshair.transform.position)
        steadyReferenceCamera.position.set(crosshair.transform.position)
    }

    override fun render(delta: Float) {
        engine.update(delta)
        updateDebugUI()
        stage.act()
        stage.draw()
        viewport.apply()
    }

    private fun updateDebugUI() {
        val fpsText = "${Gdx.graphics.framesPerSecond} FPS"
        val playerPositionText = "Position: (%.2f, %.2f)".format(player.transform.position.x, player.transform.position.y)
        val entityCount = "${engine.entities.count()} entities"
        debugLabelFPS.setText(fpsText)
        debugLabelPlayerPosition.setText(playerPositionText)
        debugEntityCountLabel.setText(entityCount)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        viewport.update(width, height)
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        world.dispose()
        stage.dispose()
        batch.dispose()
        log.debug { "'${engine.entities.size()}' entities in engine" }
    }
}