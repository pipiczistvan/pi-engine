package piengine.core.domain.assets.object.cube;

import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.render.domain.RenderPlan;
import piengine.visual.render.manager.RenderManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.RenderPlan.createPlan;

public class CubeAsset extends Asset<CubeAssetArgument> {

    private final ModelManager modelManager;

    private Model cubeModel;

    @Wire
    public CubeAsset(final RenderManager renderManager, final ModelManager modelManager) {
        super(renderManager);

        this.modelManager = modelManager;
    }

    @Override
    public void initialize() {
        cubeModel = modelManager.supply("cube", this);
    }

    @Override
    public void update(final double delta) {

    }

    @Override
    protected RenderPlan createRenderPlan() {
        return createPlan()
                .renderToWorld(arguments.cameraAsset, arguments.light, cubeModel);
    }
}
