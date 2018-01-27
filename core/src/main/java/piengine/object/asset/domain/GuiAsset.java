package piengine.object.asset.domain;

import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.GuiRenderAssetContext;

public abstract class GuiAsset<T extends AssetArgument> extends Asset<T, GuiRenderAssetContext> {

    public GuiAsset(final AssetManager assetManager) {
        super(assetManager);
    }
}
