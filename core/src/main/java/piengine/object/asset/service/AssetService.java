package piengine.object.asset.service;

import piengine.object.asset.domain.Asset;
import piengine.object.asset.domain.AssetArgument;
import piengine.object.entity.domain.Entity;
import puppeteer.Puppeteer;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class AssetService {

    private final Puppeteer puppeteer;

    @Wire
    public AssetService(final Puppeteer puppeteer) {
        this.puppeteer = puppeteer;
    }

    public <T extends Asset<A>, A extends AssetArgument> T supply(final Class<T> assetClass, final Entity parent, final A arguments) {
        T asset = puppeteer.getNewInstanceOf(assetClass);
        if (arguments != null) {
            asset.passArguments(arguments);
        }
        asset.initialize();
        asset.setParent(parent);
        asset.setupRenderPlan();

        return asset;
    }
}
