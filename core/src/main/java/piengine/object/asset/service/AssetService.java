package piengine.object.asset.service;

import piengine.object.asset.domain.Asset;
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

    public <T extends Asset> T supply(final Class<T> assetClass, final Entity parent) {
        T asset = puppeteer.getNewInstanceOf(assetClass);
        asset.initialize();
        asset.setParent(parent);
        asset.setupRenderPlan();

        return asset;
    }

}
