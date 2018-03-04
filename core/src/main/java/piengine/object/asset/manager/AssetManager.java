package piengine.object.asset.manager;

import piengine.object.asset.domain.Asset;
import piengine.object.asset.domain.AssetArgument;
import piengine.object.asset.plan.RenderAssetContext;
import piengine.object.asset.service.AssetService;
import piengine.object.entity.domain.Entity;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class AssetManager {

    private final AssetService assetService;

    @Wire
    public AssetManager(final AssetService assetService) {
        this.assetService = assetService;
    }

    public <T extends Asset<A, C>, A extends AssetArgument, C extends RenderAssetContext> T supplyNew(final Class<T> assetClass, final Entity parent, final A arguments) {
        return assetService.supplyNew(assetClass, parent, arguments);
    }

    public <T extends Asset<A, C>, A extends AssetArgument, C extends RenderAssetContext> T supplyCommon(final Class<T> assetClass, final Entity parent, final A arguments) {
        return assetService.supplyCommon(assetClass, parent, arguments);
    }
}
