package piengine.core.domain.assets.object;

import org.joml.Vector4f;
import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.AssetPlan.createPlan;

public class CubeAsset extends Asset {

    private final ModelManager modelManager;

    private Model cube;

    @Wire
    public CubeAsset(final RenderManager renderManager, final ModelManager modelManager) {
        super(renderManager);
        this.modelManager = modelManager;
    }

    @Override
    public void initialize() {
        cube = modelManager.supply("cube", this);
    }

    @Override
    public void update(double delta) {
    }

    @Override
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withModel(cube)
                .withColor(new Vector4f(0, 1, 0, 1));
    }

}
