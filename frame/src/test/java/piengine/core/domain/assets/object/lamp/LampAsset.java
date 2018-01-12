package piengine.core.domain.assets.object.lamp;

import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.object.model.domain.ModelKey;
import piengine.object.model.manager.ModelManager;
import piengine.visual.image.domain.Image;
import piengine.visual.image.manager.ImageManager;
import piengine.visual.light.Light;
import puppeteer.annotation.premade.Wire;

public class LampAsset extends Asset<LampAssetArgument> {

    private final ModelManager modelManager;
    private final ImageManager imageManager;

    private Model lampModel;
    private Image lampImage;
    private Light lampLight;

    @Wire
    public LampAsset(final ModelManager modelManager, final ImageManager imageManager) {
        this.modelManager = modelManager;
        this.imageManager = imageManager;
    }

    @Override
    public void initialize() {
        lampImage = imageManager.supply("lamp");

        lampModel = modelManager.supply(new ModelKey(this, "lamp", lampImage));
        lampModel.setScale(0.5f);

        lampLight = new Light(this);
        lampLight.setPosition(0, 5.5f, 0);
        lampLight.setColor(0.9568627451f, 0.96862745098f, 0.67843137255f);
        lampLight.setAttenuation(1f, 0.01f, 0.002f);
    }

    public Model[] getModels() {
        return new Model[]{lampModel};
    }

    public Light[] getLights() {
        return new Light[]{lampLight};
    }
}
