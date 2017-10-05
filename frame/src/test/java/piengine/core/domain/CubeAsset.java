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

    private Model cube1;
    private Model cube2;
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
        cube1 = modelManager.supply("cube", this);
        cube2 = modelManager.supply("cube", this);
        arrow = textureManager.supply("arrow");
        squareAsset = assetManager.supply(SquareAsset.class, this);

        cube1.setPosition(-1, 0, 0);
        cube1.setScale(0.5f);
        cube2.setPosition(1, 0, 0);
        cube2.setScale(0.5f);
    }

    @Override
    public void update(double delta) {
    }

    @Override
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withModel(cube1)
                .withModel(cube2)
                .withTexture(arrow);
    }

}
