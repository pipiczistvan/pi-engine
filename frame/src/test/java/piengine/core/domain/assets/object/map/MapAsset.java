package piengine.core.domain.assets.object.map;

import org.joml.Vector2i;
import org.joml.Vector3f;
import piengine.object.asset.domain.WorldAsset;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.object.terrain.domain.Terrain;
import piengine.object.terrain.manager.TerrainManager;
import piengine.object.water.domain.Water;
import piengine.object.water.manager.WaterManager;
import piengine.visual.light.domain.Light;
import piengine.visual.render.domain.plan.WorldRenderPlanBuilder;
import piengine.visual.shadow.domain.Shadow;
import piengine.visual.shadow.manager.ShadowManager;
import puppeteer.annotation.premade.Wire;

import java.util.Random;

public class MapAsset extends WorldAsset<MapAssetArgument> {

    private static final float WAVE_SPEED = 0.2f;

    private final TerrainManager terrainManager;
    private final WaterManager waterManager;
    private final ShadowManager shadowManager;
    private final ModelManager modelManager;

    private Terrain terrain;
    private Water water;
    private Light light;
    private Shadow shadow;
    private Model cubeModel;
    private Model[] treeModels = new Model[40];

    @Wire
    public MapAsset(final TerrainManager terrainManager, final WaterManager waterManager,
                    final ShadowManager shadowManager, final ModelManager modelManager) {
        this.terrainManager = terrainManager;
        this.waterManager = waterManager;
        this.shadowManager = shadowManager;
        this.modelManager = modelManager;
    }

    @Override
    public void initialize() {
        terrain = terrainManager.supply(new Vector3f(-64, 0, -64), new Vector3f(128, 5, 128), "heightmap2");
        water = waterManager.supply(arguments.viewport, new Vector2i(128, 128), new Vector3f(-64, -2, -64), new Vector3f(128, 0, 128));
        light = new Light(this);
        shadow = shadowManager.supply(light, arguments.camera, new Vector2i(2048));
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
    public WorldRenderPlanBuilder getAssetPlan() {
        return null;
    }

    @Override
    public Model[] getModels() {
        Model[] models = new Model[treeModels.length + 1];
        models[0] = cubeModel;
        for (int i = 0; i < treeModels.length; i++) {
            models[i + 1] = treeModels[i];
        }

        return models;
    }

    @Override
    public Light[] getLights() {
        return new Light[]{
                light
        };
    }

    @Override
    public Terrain[] getTerrains() {
        return new Terrain[]{
                terrain
        };
    }

    @Override
    public Shadow[] getShadows() {
        return new Shadow[]{
                shadow
        };
    }

    @Override
    public Water[] getWaters() {
        return new Water[]{
                water
        };
    }

    private void initializeAssets() {
        float waterHeight = water.position.y;

        light.setColor(1, 1, 1);
        light.setPosition(100, 200, 300);

        cubeModel.setPosition(4, 0f, -14);

        Random random = new Random();
        for (Model tree : treeModels) {
            float x;
            float y;
            float z;
            do {
                x = random.nextFloat() * 128 - 64;
                z = random.nextFloat() * 128 - 64;
                y = terrain.getHeight(x, z) - 0.1f;
            } while (y < waterHeight - 0.2);
            float scale = random.nextFloat() * 0.05f + 0.1f;

            tree.setScale(scale);
            tree.setPosition(x, y, z);
        }
    }
}
