package piengine.core.domain.assets.object.square;

import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.render.domain.RenderPlan;
import piengine.visual.render.manager.RenderManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.RenderPlan.createPlan;

public class SquareAsset extends Asset<SquareAssetArgument> {

    private final ModelManager modelManager;

    private Model squareModel;

    @Wire
    public SquareAsset(final RenderManager renderManager, final ModelManager modelManager) {
        super(renderManager);

        this.modelManager = modelManager;
    }

    @Override
    public void initialize() {
        squareModel = modelManager.supply("square", this, arguments.frameBuffer);
        squareModel.setScale(0.5f);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    protected RenderPlan createRenderPlan() {
        return createPlan()
                .renderToGui(arguments.viewport, squareModel);
    }
}
