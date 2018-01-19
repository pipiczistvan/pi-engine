package piengine.core.domain.assets.object.map;

import org.joml.Vector2i;
import org.joml.Vector3f;
import piengine.core.domain.assets.object.lamp.LampAsset;
import piengine.core.domain.assets.object.lamp.LampAssetArgument;
import piengine.object.asset.domain.WorldAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.WorldRenderAssetContext;
import piengine.object.asset.plan.WorldRenderAssetContextBuilder;
import piengine.object.entity.domain.Entity;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.object.terrain.domain.Terrain;
import piengine.object.water.domain.Water;
import piengine.object.water.manager.WaterManager;
import piengine.visual.shadow.manager.ShadowManager;
import puppeteer.annotation.premade.Wire;

public class MapAsset extends WorldAsset<MapAssetArgument> {

    private static final float WAVE_SPEED = 0.2f;

    private final WaterManager waterManager;
    private final ShadowManager shadowManager;
    private final ModelManager modelManager;
    private final AssetManager assetManager;

    private Terrain terrain;
    private Water water;
    private LampAsset lampAsset1, lampAsset2;
    //    private Light light;
//    private Shadow shadow;
    private Model cubeModel1, cubeModel2, cubeModel3, cubeModel4;
    private Model treeModel1, treeModel2, treeModel3, treeModel4;
//    private Model[] treeModels = new Model[100];

    private float wave = 0;

    @Wire
    public MapAsset(final WaterManager waterManager, final ShadowManager shadowManager, final ModelManager modelManager, final AssetManager assetManager) {
        this.waterManager = waterManager;
        this.shadowManager = shadowManager;
        this.modelManager = modelManager;
        this.assetManager = assetManager;
    }

    @Override
    public void initialize() {
        terrain = arguments.terrain;
        water = waterManager.supply(arguments.viewport, new Vector2i(128, 128), new Vector3f(-128, -4, -128), new Vector3f(256, 0, 256));
//        light = new Light(this);
//        shadow = shadowManager.supply(light, arguments.camera, new Vector2i(2048));
        lampAsset1 = assetManager.supply(LampAsset.class, this, new LampAssetArgument());
        lampAsset2 = assetManager.supply(LampAsset.class, this, new LampAssetArgument());

        cubeModel1 = modelManager.supply(this, "cube", false);
        cubeModel2 = modelManager.supply(this, "cube", false);
        cubeModel3 = modelManager.supply(this, "cube", false);
        cubeModel4 = modelManager.supply(this, "cube", false);

        treeModel1 = modelManager.supply(this, "lowPolyTree", "lowPolyTree", false);
        treeModel2 = modelManager.supply(this, "lowPolyTree", "lowPolyTree", false);
        treeModel3 = modelManager.supply(this, "lowPolyTree", "lowPolyTree", false);
        treeModel4 = modelManager.supply(this, "lowPolyTree", "lowPolyTree", false);

//        for (int i = 0; i < treeModels.length; i++) {
//            treeModels[i] = modelManager.supply(this, "lowPolyTree", "lowPolyTree", false);
//        }

        initializeAssets();
    }

    @Override
    public void update(final double delta) {
        water.waveFactor += WAVE_SPEED * delta;

        wave += delta;

        cubeModel1.translateRotate(0, (float) (Math.sin(wave) * 0.01f), 0, (float) (5f * delta), (float) (10f * delta), (float) (15f * delta));
        cubeModel2.translateRotate(0, (float) (Math.sin(wave - 0.5) * 0.01f), 0, (float) (5f * delta), (float) (10f * delta), (float) (15f * delta));
        cubeModel3.translateRotate(0, (float) (Math.sin(wave - 1) * 0.01f), 0, (float) (5f * delta), (float) (10f * delta), (float) (15f * delta));
        cubeModel4.translateRotate(0, (float) (Math.sin(wave - 1.5) * 0.01f), 0, (float) (5f * delta), (float) (10f * delta), (float) (15f * delta));
    }

    @Override
    public WorldRenderAssetContext getAssetContext() {
        return WorldRenderAssetContextBuilder.create()
                .loadModels(treeModel1, treeModel2, treeModel3, treeModel4)
                .loadModels(cubeModel1)
                .loadTerrains(terrain)
                .loadWaters(water)
                .loadAssets(lampAsset1)
//                .loadLights(light)
//                .loadShadows(shadow)
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
//        placeEntityOnTerrain(lampAsset2, -6, 0, 0);

//        Random random = new Random();
//        for (Model tree : treeModels) {
//            float x;
//            float y;
//            float z;
//            do {
//                x = random.nextFloat() * 256 - 128;
//                z = random.nextFloat() * 256 - 128;
//                y = terrain.getHeight(x, z) - 0.2f;
//            } while (y < waterHeight - 0.2);
//            float scale = random.nextFloat() * 0.1f + 0.2f;
//
//            tree.setScale(scale);
//            tree.setPosition(x, y, z);
//        }
    }
}
