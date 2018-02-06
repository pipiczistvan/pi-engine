package piengine.object.asset.domain;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Updatable;
import piengine.core.base.domain.Entity;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.RenderAssetContext;

public abstract class Asset<A extends AssetArgument, C extends RenderAssetContext> extends Entity implements Initializable, Updatable {

    private final AssetManager assetManager;
    protected A arguments;

    protected Asset(final AssetManager assetManager) {
        super(null);

        this.assetManager = assetManager;
    }

    public void passArguments(final A arguments) {
        this.arguments = arguments;
    }

    public abstract C getAssetContext();

    protected <T extends Asset<A, C>, A extends AssetArgument, C extends RenderAssetContext> T createAsset(final Class<T> assetClass, final A arguments) {
        return assetManager.supply(assetClass, this, arguments);
    }

    @Override
    public void update(final float delta) {
    }
}
