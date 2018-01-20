package piengine.object.asset.domain;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Updatable;
import piengine.object.asset.plan.RenderAssetContext;
import piengine.object.entity.domain.Entity;

public abstract class Asset<A extends AssetArgument, C extends RenderAssetContext> extends Entity implements Initializable, Updatable {

    protected A arguments;

    protected Asset() {
        super(null);
    }

    public void passArguments(final A arguments) {
        this.arguments = arguments;
    }

    @Override
    public void update(final float delta) {
    }

    public abstract C getAssetContext();
}
