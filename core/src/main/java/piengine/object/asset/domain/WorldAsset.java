package piengine.object.asset.domain;

import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.WorldRenderAssetContext;

public abstract class WorldAsset<T extends AssetArgument> extends Asset<T, WorldRenderAssetContext> {

    public WorldAsset(final AssetManager assetManager) {
        super(assetManager);
    }
}
