package piengine.object.asset.domain;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Updatable;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.RenderAssetContext;
import piengine.object.entity.domain.Entity;

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
        return assetManager.supplyNew(assetClass, this, arguments);
    }

    protected <T extends Asset<A, C>, A extends AssetArgument, C extends RenderAssetContext> T supplyAsset(final Class<T> assetClass, final A arguments) {
        return assetManager.supplyCommon(assetClass, this, arguments);
    }

    @Override
    public void update(final float delta) {
    }
}
