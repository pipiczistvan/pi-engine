package piengine.core.domain.assets.object.lamp;

import org.joml.Vector2i;
import piengine.object.asset.domain.WorldAsset;
import piengine.object.asset.plan.WorldRenderAssetContext;
import piengine.object.asset.plan.WorldRenderAssetContextBuilder;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.light.domain.Light;
import piengine.visual.pointshadow.domain.PointShadow;
import piengine.visual.pointshadow.manager.PointShadowManager;
import puppeteer.annotation.premade.Wire;

public class LampAsset extends WorldAsset<LampAssetArgument> {

    private final ModelManager modelManager;
    private final PointShadowManager pointShadowManager;

    private Model lampModel;
    private Light lampLight;
    public PointShadow lampPointShadow;

    @Wire
    public LampAsset(final ModelManager modelManager, final PointShadowManager pointShadowManager) {
        this.modelManager = modelManager;
        this.pointShadowManager = pointShadowManager;
    }

    @Override
    public void initialize() {
        lampModel = modelManager.supply(this, "lamp", "lamp", true);

        lampLight = new Light(this);
        lampLight.setPosition(0, 10, 0);
        lampLight.setColor(0.9568627451f, 0.96862745098f, 0.67843137255f);
        lampLight.setAttenuation(1f, 0.01f, 0.002f);

        lampPointShadow = pointShadowManager.supply(lampLight, new Vector2i(1024));
    }

    @Override
    public WorldRenderAssetContext getAssetContext() {
        return WorldRenderAssetContextBuilder.create()
                .loadModels(lampModel)
                .loadLights(lampLight)
                .loadPointShadows(lampPointShadow)
                .build();
    }
}
