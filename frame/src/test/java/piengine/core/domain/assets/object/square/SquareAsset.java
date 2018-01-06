package piengine.core.domain.assets.object.square;

import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import puppeteer.annotation.premade.Wire;

public class SquareAsset extends Asset<SquareAssetArgument> {

    private final ModelManager modelManager;

    private Model squareModel;

    @Wire
    public SquareAsset(final ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    @Override
    public void initialize() {
        squareModel = modelManager.supply("square", this, arguments.frameBuffer);
    }

    @Override
    public Model[] getModels() {
        return new Model[]{
                squareModel
        };
    }
}
