package piengine.core.domain.assets.object.map;

import org.joml.Vector2i;
import piengine.core.utils.ColorUtils;
import piengine.object.asset.domain.WorldAsset;
import piengine.object.model.domain.Model;
import piengine.object.model.domain.ModelKey;
import piengine.object.model.manager.ModelManager;
import piengine.object.terrain.domain.Terrain;
import piengine.object.terrain.domain.TerrainKey;
import piengine.object.terrain.manager.TerrainManager;
import piengine.object.water.domain.Water;
import piengine.object.water.domain.WaterKey;
import piengine.object.water.manager.WaterManager;
import piengine.visual.image.domain.Image;
import piengine.visual.image.manager.ImageManager;
import piengine.visual.light.Light;
import piengine.visual.shadow.domain.Shadow;
import piengine.visual.shadow.domain.ShadowKey;
import piengine.visual.shadow.manager.ShadowManager;
import puppeteer.annotation.premade.Wire;

import java.util.Random;

public class MapAsset extends WorldAsset<MapAssetArgument> {

    private static final float WAVE_SPEED = 0.2f;

    private final TerrainManager terrainManager;
    private final WaterManager waterManager;
    private final ShadowManager shadowManager;
    private final ModelManager modelManager;
    private final ImageManager imageManager;

    private Terrain terrain;
    private Water water;
    private Light light;
    private Shadow shadow;
    private Model cubeModel;
    private Image treeImage;
    private Model[] treeModels = new Model[40];

    @Wire
    public MapAsset(final TerrainManager terrainManager, final WaterManager waterManager,
                    final ShadowManager shadowManager, final ModelManager modelManager,
                    final ImageManager imageManager) {
        this.terrainManager = terrainManager;
        this.waterManager = waterManager;
        this.shadowManager = shadowManager;
        this.modelManager = modelManager;
        this.imageManager = imageManager;
    }

    @Override
    public void initialize() {
        terrain = terrainManager.supply(new TerrainKey(this, "heightmap2"));
        water = waterManager.supply(new WaterKey(this, arguments.viewport, new Vector2i(128, 128)));
        light = new Light(this);
        shadow = shadowManager.supply(new ShadowKey(light, arguments.camera, new Vector2i(2048)));
        cubeModel = modelManager.supply(new ModelKey(this, "cube", ColorUtils.RED));
        treeImage = imageManager.supply("lowPolyTree");
        for (int i = 0; i < treeModels.length; i++) {
            treeModels[i] = modelManager.supply(new ModelKey(this, "lowPolyTree", treeImage));
        }

        initializeAssets();
    }

    @Override
    public void update(final double delta) {
        water.waveFactor += WAVE_SPEED * delta;

        cubeModel.rotate((float) (5f * delta), (float) (10f * delta), (float) (15f * delta));
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
        terrain.setPosition(-64, 0, -64);
        terrain.setScale(128, 5, 128);

        water.setScale(128, 0, 128);
        water.setPosition(-64, -2.0f, -64);
        float waterHeight = water.getPosition().y;

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
