package piengine.object.asset.domain;

import piengine.core.base.RenderPlanner;
import piengine.core.base.api.Initializable;
import piengine.core.base.api.Updatable;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;

public abstract class Asset<T extends AssetArgument> extends RenderPlanner<AssetPlan> implements Initializable, Updatable {

    protected T arguments;

    public Asset(final RenderManager renderManager) {
        super(renderManager);
    }

    public void passArguments(final T arguments) {
        this.arguments = arguments;
    }
}
