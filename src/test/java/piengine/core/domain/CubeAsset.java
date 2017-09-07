package piengine.core.domain;

import piengine.object.asset.domain.Asset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.manager.TextureManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.AssetPlan.createPlan;

public class CubeAsset extends Asset {

    private final ModelManager modelManager;
    private final TextureManager textureManager;
    private final AssetManager assetManager;

    private Model cube;
    private Texture arrow;
    private SquareAsset squareAsset;

    @Wire
    public CubeAsset(final RenderManager renderManager, final ModelManager modelManager,
                     final TextureManager textureManager, final AssetManager assetManager) {
        super(renderManager);
        this.modelManager = modelManager;
        this.textureManager = textureManager;
        this.assetManager = assetManager;
    }

    @Override
    public void initialize() {
        cube = modelManager.supply("cube", this);
        arrow = textureManager.supply("arrow");
        squareAsset = assetManager.supply(SquareAsset.class, this);

//        squareAsset.position.set(-3, 0, 0);
    }

    @Override
    public void update(double delta) {
//        cube.rotation.add(30f * (float) delta, 50f * (float) delta, 70f * (float) delta);
    }

    @Override
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withModel(cube)
                .withTexture(arrow)
                .withAsset(squareAsset);
    }

}
