package piengine.object.asset.service;

import piengine.core.base.api.Service;
import piengine.core.base.api.Updatable;
import piengine.object.asset.domain.Asset;
import piengine.object.asset.domain.AssetArgument;
import piengine.object.asset.plan.RenderAssetContext;
import piengine.object.entity.domain.Entity;
import puppeteer.Puppeteer;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.ArrayList;
import java.util.List;

@Component
public class AssetService implements Service, Updatable {

    private final Puppeteer puppeteer;
    private final List<Asset> assets;

    @Wire
    public AssetService(final Puppeteer puppeteer) {
        this.puppeteer = puppeteer;
        this.assets = new ArrayList<>();
    }

    public <T extends Asset<A, C>, A extends AssetArgument, C extends RenderAssetContext> T supplyNew(final Class<T> assetClass, final Entity parent, final A arguments) {
        T asset = puppeteer.getNewInstanceOf(assetClass);
        return supply(asset, parent, arguments);
    }

    public <T extends Asset<A, C>, A extends AssetArgument, C extends RenderAssetContext> T supplyCommon(final Class<T> assetClass, final Entity parent, final A arguments) {
        T asset = puppeteer.getInstanceOf(assetClass);
        return supply(asset, parent, arguments);
    }

    private  <T extends Asset<A, C>, A extends AssetArgument, C extends RenderAssetContext> T supply(final T asset, final Entity parent, final A arguments) {
        if (arguments != null) {
            asset.passArguments(arguments);
        }
        asset.initialize();
        parent.addChild(asset);

        assets.add(asset);
        return asset;
    }

    @Override
    public void update(final float delta) {
        assets.forEach(asset -> asset.update(delta));
    }
}
