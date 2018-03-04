package piengine.core.architecture.scene.domain;

import piengine.core.base.RenderPlanner;
import piengine.core.base.api.Initializable;
import piengine.core.base.api.Resizable;
import piengine.core.base.api.Updatable;
import piengine.object.asset.domain.Asset;
import piengine.object.asset.domain.AssetArgument;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.RenderAssetContext;
import piengine.visual.render.domain.plan.RenderPlan;
import piengine.visual.render.manager.RenderManager;

public abstract class Scene extends RenderPlanner<RenderPlan> implements Initializable, Updatable, Resizable {

    private final AssetManager assetManager;

    public Scene(final RenderManager renderManager, final AssetManager assetManager) {
        super(renderManager);

        this.assetManager = assetManager;
    }

    protected <T extends Asset<A, C>, A extends AssetArgument, C extends RenderAssetContext> T createAsset(final Class<T> assetClass, final A arguments) {
        return assetManager.supplyNew(assetClass, this, arguments);
    }

    protected <T extends Asset<A, C>, A extends AssetArgument, C extends RenderAssetContext> T supplyAsset(final Class<T> assetClass, final A arguments) {
        return assetManager.supplyCommon(assetClass, this, arguments);
    }

    @Override
    public void initialize() {
        createAssets();
        initializeAssets();
    }

    protected void createAssets() {
    }

    protected void initializeAssets() {
    }
}
