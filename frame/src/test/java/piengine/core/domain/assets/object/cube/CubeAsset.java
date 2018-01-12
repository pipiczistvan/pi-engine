package piengine.core.domain.assets.object.cube;

import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.object.model.domain.ModelKey;
import piengine.object.model.manager.ModelManager;
import puppeteer.annotation.premade.Wire;

public class CubeAsset extends Asset<CubeAssetArgument> {

    private final ModelManager modelManager;

    private Model cubeModel;

    @Wire
    public CubeAsset(final ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    @Override
    public void initialize() {
        cubeModel = modelManager.supply(new ModelKey(this, "cube"));
    }

    public Model[] getModels() {
        return new Model[]{
                cubeModel
        };
    }
}
