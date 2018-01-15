package piengine.object.asset.domain;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Updatable;
import piengine.object.entity.domain.Entity;
import piengine.visual.render.domain.plan.RenderPlanBuilder;

public abstract class Asset<T extends AssetArgument, B extends RenderPlanBuilder> extends Entity implements Initializable, Updatable {

    protected T arguments;

    public Asset() {
        super(null);
    }

    public void passArguments(final T arguments) {
        this.arguments = arguments;
    }

    @Override
    public void update(double delta) {
    }

    public abstract B getAssetPlan();
}
