package piengine.visual.camera.asset;

import piengine.object.asset.domain.Asset;
import piengine.visual.camera.domain.Camera;
import piengine.visual.render.domain.RenderPlan;
import piengine.visual.render.manager.RenderManager;

import static piengine.visual.render.domain.RenderPlan.createPlan;

public abstract class CameraAsset<C extends Camera> extends Asset {

    public final C camera;

    public CameraAsset(final RenderManager renderManager) {
        super(renderManager);

        this.camera = getCamera();
    }

    protected abstract C getCamera();

    @Override
    protected RenderPlan createRenderPlan() {
        return createPlan();
    }
}
