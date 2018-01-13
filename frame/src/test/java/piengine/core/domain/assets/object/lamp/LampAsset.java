package piengine.core.domain.assets.object.lamp;

import piengine.object.asset.domain.WorldAsset;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.light.Light;
import puppeteer.annotation.premade.Wire;

public class LampAsset extends WorldAsset<LampAssetArgument> {

    private final ModelManager modelManager;

    private Model lampModel;
    private Light lampLight;

    @Wire
    public LampAsset(final ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    @Override
    public void initialize() {
        lampModel = modelManager.supply(this, "lamp", "lamp");
        lampModel.setScale(0.5f);

        lampLight = new Light(this);
        lampLight.setPosition(0, 5.5f, 0);
        lampLight.setColor(0.9568627451f, 0.96862745098f, 0.67843137255f);
        lampLight.setAttenuation(1f, 0.01f, 0.002f);
    }

    @Override
    public Model[] getModels() {
        return new Model[]{lampModel};
    }

    @Override
    public Light[] getLights() {
        return new Light[]{lampLight};
    }
}
