package piengine.object.asset.manager;

import piengine.object.asset.domain.Asset;
import piengine.object.asset.domain.AssetArgument;
import piengine.object.asset.service.AssetService;
import piengine.object.entity.domain.Entity;
import piengine.visual.render.domain.plan.RenderPlanBuilder;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class AssetManager {

    private final AssetService assetService;

    @Wire
    public AssetManager(final AssetService assetService) {
        this.assetService = assetService;
    }

    public <T extends Asset<A, B>, A extends AssetArgument, B extends RenderPlanBuilder> T supply(final Class<T> assetClass, final Entity parent, final A arguments) {
        return assetService.supply(assetClass, parent, arguments);
    }
}
