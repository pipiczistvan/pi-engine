package piengine.core.domain.assets.object.map;

import org.joml.Vector2i;
import org.joml.Vector3f;
import piengine.core.base.type.color.Color;
import piengine.core.domain.assets.object.lamp.LampAsset;
import piengine.core.domain.assets.object.lamp.LampAssetArgument;
import piengine.core.input.domain.KeyEventType;
import piengine.core.input.manager.InputManager;
import piengine.core.utils.ColorUtils;
import piengine.object.animatedmodel.domain.AnimatedModel;
import piengine.object.animatedmodel.manager.AnimatedModelManager;
import piengine.object.animation.domain.Animation;
import piengine.object.animation.manager.AnimationManager;
import piengine.object.animator.domain.Animator;
import piengine.object.animator.manager.AnimatorManager;
import piengine.object.asset.domain.WorldAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.WorldRenderAssetContext;
import piengine.object.asset.plan.WorldRenderAssetContextBuilder;
import piengine.object.entity.domain.Entity;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.object.particlesystem.domain.ParticleSystem;
import piengine.object.particlesystem.manager.ParticleSystemManager;
import piengine.object.terrain.domain.Terrain;
import piengine.object.water.domain.Water;
import piengine.object.water.manager.WaterManager;
import piengine.visual.lighting.directional.light.domain.DirectionalLight;
import piengine.visual.lighting.directional.light.manager.DirectionalLightManager;
import puppeteer.annotation.premade.Wire;

import java.util.Random;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.WATER_WAVE_SPEED;
import static piengine.core.domain.InitScene.TERRAIN_SCALE;
import static piengine.core.domain.InitScene.WATER_SCALE;

public class MapAsset extends WorldAsset<MapAssetArgument> {

    private static final float WAVE_SPEED = get(WATER_WAVE_SPEED);
    private static final Color WATER_COLOR = ColorUtils.createNormalizedColor(20, 192, 255);

    private final WaterManager waterManager;
    private final ModelManager modelManager;
    private final AnimatedModelManager animatedModelManager;
    private final AnimationManager animationManager;
    private final AnimatorManager animatorManager;
    private final InputManager inputManager;
    private final DirectionalLightManager directionalLightManager;
    private final ParticleSystemManager particleSystemManager;

    private Terrain terrain;
    private Water water;
    private LampAsset lampAsset1, lampAsset2;
    private Model cubeModel1, cubeModel2, cubeModel3, cubeModel4;
    private Model treeModel1, treeModel2, treeModel3, treeModel4;
    private Model[] treeModels = new Model[100];
    private AnimatedModel peasantModel;
    private Animation peasantAnimation;
    private Animator peasantAnimator;
    private DirectionalLight sun;
    private ParticleSystem particleSystem;

    private float wave = 0;

    @Wire
    public MapAsset(final AssetManager assetManager, final ModelManager modelManager,
                    final WaterManager waterManager, final AnimatedModelManager animatedModelManager,
                    final AnimationManager animationManager, final AnimatorManager animatorManager,
                    final InputManager inputManager, final DirectionalLightManager directionalLightManager,
                    final ParticleSystemManager particleSystemManager) {
        super(assetManager);

        this.waterManager = waterManager;
        this.modelManager = modelManager;
        this.animatedModelManager = animatedModelManager;
        this.animationManager = animationManager;
        this.animatorManager = animatorManager;
        this.inputManager = inputManager;
        this.directionalLightManager = directionalLightManager;
        this.particleSystemManager = particleSystemManager;
    }

    @Override
    public void initialize() {
        terrain = arguments.terrain;
        water = waterManager.supply(arguments.viewport, new Vector2i(WATER_SCALE, WATER_SCALE), new Vector3f(-TERRAIN_SCALE / 2, -4, -TERRAIN_SCALE / 2), new Vector3f(TERRAIN_SCALE, 0, TERRAIN_SCALE), WATER_COLOR);

        lampAsset1 = createAsset(LampAsset.class, new LampAssetArgument());
        lampAsset2 = createAsset(LampAsset.class, new LampAssetArgument());

        cubeModel1 = modelManager.supply(this, "box", "box", false);
        cubeModel2 = modelManager.supply(this, "cube", false);
        cubeModel3 = modelManager.supply(this, "cube", false);
        cubeModel4 = modelManager.supply(this, "cube", false);

        treeModel1 = modelManager.supply(this, "lowPolyTree", "lowPolyTree", false);
        treeModel2 = modelManager.supply(this, "lowPolyTree", "lowPolyTree", false);
        treeModel3 = modelManager.supply(this, "lowPolyTree", "lowPolyTree", false);
        treeModel4 = modelManager.supply(this, "lowPolyTree", "lowPolyTree", false);

        for (int i = 0; i < treeModels.length; i++) {
            treeModels[i] = modelManager.supply(this, "lowPolyTree", "lowPolyTree", false);
        }

        peasantModel = animatedModelManager.supply(this, "peasant", "peasant");
        peasantAnimation = animationManager.supply("peasant");
        peasantAnimator = animatorManager.supply(peasantModel);

        inputManager.addEvent(GLFW_KEY_R, KeyEventType.PRESS, () -> peasantAnimator.doAnimation(peasantAnimation));
        inputManager.addEvent(GLFW_KEY_R, KeyEventType.RELEASE, () -> peasantAnimator.doAnimation(null));

        sun = directionalLightManager.supply(this, new Color(1.0f, 0.9f, 0.6f), arguments.camera, new Vector2i(2048));
        sun.setPosition(1000, 1000, 300);

        particleSystem = particleSystemManager.supply(this, arguments.camera, 50, 7, 0.01f, 1, "fire", 8);
        particleSystem.scale(2, 2, 2);

        initializeAssets();
    }

    @Override
    public void update(final float delta) {
        water.waveFactor += WAVE_SPEED * delta;

        wave += delta;

        cubeModel1.translateRotate(0, (float) (Math.sin(wave) * 0.01f), 0, 5f * delta, 10f * delta, 15f * delta);
        cubeModel2.translateRotate(0, (float) (Math.sin(wave - 0.5) * 0.01f), 0, 5f * delta, 10f * delta, 15f * delta);
        cubeModel3.translateRotate(0, (float) (Math.sin(wave - 1) * 0.01f), 0, 5f * delta, 10f * delta, 15f * delta);
        cubeModel4.translateRotate(0, (float) (Math.sin(wave - 1.5) * 0.01f), 0, 5f * delta, 10f * delta, 15f * delta);
    }

    @Override
    public WorldRenderAssetContext getAssetContext() {
        return WorldRenderAssetContextBuilder.create()
                .loadModels(treeModel1, treeModel2, treeModel3, treeModel4)
                .loadModels(cubeModel1)
                .loadTerrains(terrain)
                .loadWaters(water)
                .loadModels(treeModels)
                .loadAnimatedModels(peasantModel)
//                .loadDirectionalLights(sun)
                .loadAssets(lampAsset1)
//                .loadAssets(lampAsset2)
                .loadParticleSystems(particleSystem)
                .build();
    }

    private void placeEntityOnTerrain(final Entity entity, final float x, final float z, final float yOffset) {
        float y = terrain.getHeight(x, z) + yOffset;
        entity.setPosition(x, y, z);
    }

    private void initializeAssets() {
        float waterHeight = water.position.y;

//        light.setColor(1, 1, 1);
//        light.setPosition(100, 200, 300);

        placeEntityOnTerrain(cubeModel1, 10, 10, 6);
//        placeEntityOnTerrain(cubeModel2, 4, -2, 6);
//        placeEntityOnTerrain(cubeModel3, 4, 2, 6);
//        placeEntityOnTerrain(cubeModel4, 4, 6, 6);

        placeEntityOnTerrain(treeModel1, 12, 5, 0);
        placeEntityOnTerrain(treeModel2, 0, 12, 0);
        placeEntityOnTerrain(treeModel3, -12, 5, 0);
        placeEntityOnTerrain(treeModel4, 2, -12, 0);

        treeModel1.scale(0.3f, 0.3f, 0.3f);
        treeModel2.scale(0.3f, 0.3f, 0.3f);
        treeModel3.scale(0.3f, 0.3f, 0.3f);
        treeModel4.scale(0.3f, 0.3f, 0.3f);

        placeEntityOnTerrain(lampAsset1, 0, 0, 0);
        placeEntityOnTerrain(lampAsset2, -6, 0, 0);

        Random random = new Random();
        for (Model tree : treeModels) {
            float x;
            float y;
            float z;
            do {
                x = random.nextFloat() * 256 - 128;
                z = random.nextFloat() * 256 - 128;
                y = terrain.getHeight(x, z) - 0.2f;
            } while (y < waterHeight - 0.2);
            float scale = random.nextFloat() * 0.1f + 0.2f;

            tree.setScale(scale);
            tree.setPosition(x, y, z);
        }

        peasantModel.setScale(0.5f);
        placeEntityOnTerrain(peasantModel, 5, -5, 5);
    }
}
