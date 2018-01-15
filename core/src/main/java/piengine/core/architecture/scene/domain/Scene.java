package piengine.core.architecture.scene.domain;

import piengine.core.base.RenderPlanner;
import piengine.core.base.api.Initializable;
import piengine.core.base.api.Updatable;
import piengine.object.asset.domain.Asset;
import piengine.object.asset.domain.AssetArgument;
import piengine.object.asset.manager.AssetManager;
import piengine.visual.render.domain.plan.RenderPlan;
import piengine.visual.render.domain.plan.RenderPlanBuilder;
import piengine.visual.render.manager.RenderManager;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene extends RenderPlanner<RenderPlan> implements Initializable, Updatable {

    private final AssetManager assetManager;
    private final List<Asset> assets;

    public Scene(final RenderManager renderManager, final AssetManager assetManager) {
        super(renderManager);

        this.assetManager = assetManager;
        this.assets = new ArrayList<>();
    }

    protected <T extends Asset<A, B>, A extends AssetArgument, B extends RenderPlanBuilder> T createAsset(final Class<T> assetClass, final A arguments) {
        final T asset = assetManager.supply(assetClass, this, arguments);
        assets.add(asset);
        return asset;
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

    @Override
    public void update(double delta) {
        assets.forEach(asset -> asset.update(delta));
    }

}
