package piengine.core.domain.assets.object.map;

import org.joml.Vector2i;
import org.joml.Vector3f;
import piengine.core.domain.assets.object.lamp.LampAsset;
import piengine.core.domain.assets.object.lamp.LampAssetArgument;
import piengine.object.asset.domain.WorldAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.WorldRenderAssetContext;
import piengine.object.asset.plan.WorldRenderAssetContextBuilder;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.object.terrain.domain.Terrain;
import piengine.object.water.domain.Water;
import piengine.object.water.manager.WaterManager;
import piengine.visual.shadow.manager.ShadowManager;
import puppeteer.annotation.premade.Wire;

import java.util.Random;

public class MapAsset extends WorldAsset<MapAssetArgument> {

    private static final float WAVE_SPEED = 0.2f;

    private final WaterManager waterManager;
    private final ShadowManager shadowManager;
    private final ModelManager modelManager;
    private final AssetManager assetManager;

    private Terrain terrain;
    private Water water;
    private LampAsset lampAsset;
//    private Light light;
//    private Shadow shadow;
    private Model cubeModel;
    private Model[] treeModels = new Model[100];

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
        lampAsset = assetManager.supply(LampAsset.class, this, new LampAssetArgument());
        cubeModel = modelManager.supply(this, "cube");
        for (int i = 0; i < treeModels.length; i++) {
            treeModels[i] = modelManager.supply(this, "lowPolyTree", "lowPolyTree");
        }

        initializeAssets();
    }

    @Override
    public void update(final double delta) {
        water.waveFactor += WAVE_SPEED * delta;

        cubeModel.rotate((float) (5f * delta), (float) (10f * delta), (float) (15f * delta));
    }

    @Override
    public WorldRenderAssetContext getAssetContext() {
        return WorldRenderAssetContextBuilder.create()
                .loadModels(treeModels)
                .loadModels(cubeModel)
                .loadTerrains(terrain)
                .loadWaters(water)
                .loadAssets(lampAsset)
//                .loadLights(light)
//                .loadShadows(shadow)
                .build();
    }

    private void initializeAssets() {
        float waterHeight = water.position.y;

//        light.setColor(1, 1, 1);
//        light.setPosition(100, 200, 300);

        cubeModel.setPosition(4, 0f, -4);

        lampAsset.setPosition(0, terrain.getHeight(0, 0), 0);

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
    }
}
