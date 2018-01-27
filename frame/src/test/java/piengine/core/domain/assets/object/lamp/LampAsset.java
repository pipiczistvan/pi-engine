package piengine.core.domain.assets.object.lamp;

import org.joml.Vector2i;
import org.joml.Vector3f;
import piengine.core.base.type.color.Color;
import piengine.object.asset.domain.WorldAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.WorldRenderAssetContext;
import piengine.object.asset.plan.WorldRenderAssetContextBuilder;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.lighting.point.light.domain.PointLight;
import piengine.visual.lighting.point.light.manager.PointLightManager;
import puppeteer.annotation.premade.Wire;

public class LampAsset extends WorldAsset<LampAssetArgument> {

    private final ModelManager modelManager;
    private final PointLightManager pointLightManager;

    private Model lampModel;
    private PointLight lampLight;

    @Wire
    public LampAsset(final AssetManager assetManager, final ModelManager modelManager, final PointLightManager pointLightManager) {
        super(assetManager);

        this.modelManager = modelManager;
        this.pointLightManager = pointLightManager;
    }

    @Override
    public void initialize() {
        lampModel = modelManager.supply(this, "lamp", "lamp", true);

        lampLight = pointLightManager.supply(this,
                new Color(0.9568627451f, 0.96862745098f, 0.67843137255f),
                new Vector3f(1f, 0.01f, 0.002f),
                new Vector2i(512)
        );
        lampLight.setPosition(0, 10, 0);
    }

    @Override
    public WorldRenderAssetContext getAssetContext() {
        return WorldRenderAssetContextBuilder.create()
                .loadModels(lampModel)
                .loadPointLights(lampLight)
                .build();
    }
}
