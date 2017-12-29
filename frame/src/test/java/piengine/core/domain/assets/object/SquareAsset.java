package piengine.core.domain.assets.object;

import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.AssetPlan.createPlan;

public class SquareAsset extends Asset {

    private final ModelManager modelManager;

    private Model square;
    private FrameBuffer frameBuffer;

    @Wire
    public SquareAsset(final RenderManager renderManager, final ModelManager modelManager) {
        super(renderManager);
        this.modelManager = modelManager;
    }

    @Override
    public void initialize() {
        square = modelManager.supply("square", this);

        square.setScale(0.5f);
        square.setPosition(-0.5f, -0.5f, 0.0f);
    }

    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;
    }

    @Override
    public void update(double delta) {

    }

    @Override
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withModel(square);
    }

}
