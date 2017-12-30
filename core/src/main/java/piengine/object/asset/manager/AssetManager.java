package piengine.object.asset.manager;

import piengine.object.asset.domain.Asset;
import piengine.object.asset.domain.AssetArgument;
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

    public <T extends Asset> T supply(final Class<T> assetClass, final Entity parent) {
        return this.supply(assetClass, parent, null);
    }

    public <T extends Asset> T supply(final Class<T> assetClass, final Entity parent, final AssetArgument arguments) {
        return assetService.supply(assetClass, parent, arguments);
    }
}
