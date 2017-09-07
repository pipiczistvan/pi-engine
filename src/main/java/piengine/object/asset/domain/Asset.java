package piengine.object.asset.domain;

import piengine.core.base.RenderPlanner;
import piengine.core.base.api.Initializable;
import piengine.core.base.api.Updatable;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;

public abstract class Asset extends RenderPlanner<AssetPlan> implements Initializable, Updatable {

    public Asset(final RenderManager renderManager) {
        super(renderManager);
    }

}
