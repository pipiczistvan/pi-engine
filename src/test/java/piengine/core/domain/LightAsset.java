package piengine.core.domain;

import piengine.object.asset.domain.Asset;
import piengine.visual.light.Light;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.AssetPlan.createPlan;

public class LightAsset extends Asset {

    private final Light light;

    @Wire
    public LightAsset(final RenderManager renderManager) {
        super(renderManager);

        this.light = new Light(this);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void update(double delta) {
    }

    @Override
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withLight(light);
    }

}
