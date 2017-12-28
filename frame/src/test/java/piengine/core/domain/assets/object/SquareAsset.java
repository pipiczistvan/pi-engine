package piengine.core.domain.assets.object;

import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.manager.TextureManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.AssetPlan.createPlan;

public class SquareAsset extends Asset {

    private final ModelManager modelManager;
    private final TextureManager textureManager;

    private Model square;
    private Texture arrow;

    @Wire
    public SquareAsset(final RenderManager renderManager, final ModelManager modelManager,
                       final TextureManager textureManager) {
        super(renderManager);
        this.modelManager = modelManager;
        this.textureManager = textureManager;
    }

    @Override
    public void initialize() {
        square = modelManager.supply("square", this);
        arrow = textureManager.supply("arrow");

        square.setScale(0.5f);
        square.setPosition(-0.5f, -0.5f, 0.0f);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withModel(square)
                .withTexture(arrow);
    }

}
