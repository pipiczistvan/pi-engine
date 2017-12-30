package piengine.core.domain.assets.object.square;

import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.AssetPlan.createPlan;

public class SquareAsset extends Asset<SquareAssetArgument> {

    private final ModelManager modelManager;

    private Model square;

    @Wire
    public SquareAsset(final RenderManager renderManager, final ModelManager modelManager) {
        super(renderManager);

        this.modelManager = modelManager;
    }

    @Override
    public void initialize() {
        square = modelManager.supply("square", this);
    }

    @Override
    public void update(double delta) {

    }

    public static SquareAssetArgument createArguments(final FrameBuffer frameBuffer) {
        return new SquareAssetArgument(frameBuffer);
    }

    @Override
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withTexture(arguments.frameBuffer)
                .withModel(square);
    }
}
