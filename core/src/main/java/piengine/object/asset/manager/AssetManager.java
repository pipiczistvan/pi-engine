package piengine.object.asset.manager;

import piengine.core.base.domain.Entity;
import piengine.object.asset.domain.Asset;
import piengine.object.asset.domain.AssetArgument;
import piengine.object.asset.plan.RenderAssetContext;
import piengine.object.asset.service.AssetService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class AssetManager {

    private final AssetService assetService;

    @Wire
    public AssetManager(final AssetService assetService) {
        this.assetService = assetService;
    }

    public <T extends Asset<A, C>, A extends AssetArgument, C extends RenderAssetContext> T supply(final Class<T> assetClass, final Entity parent, final A arguments) {
        return assetService.supply(assetClass, parent, arguments);
    }
}
