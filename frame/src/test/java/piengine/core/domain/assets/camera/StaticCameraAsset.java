package piengine.core.domain.assets.camera;

import org.joml.Vector2i;
import piengine.object.asset.domain.Asset;
import piengine.visual.camera.StaticCamera;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.framebuffer.domain.FrameBufferData;
import piengine.visual.framebuffer.manager.FrameBufferManager;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import puppeteer.annotation.premade.Wire;

import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.*;
import static piengine.visual.camera.ProjectionType.ORTHOGRAPHIC;
import static piengine.visual.render.domain.AssetPlan.createPlan;

public class StaticCameraAsset extends Asset {

    private final StaticCamera camera;

    @Wire
    public StaticCameraAsset(final RenderManager renderManager, final FrameBufferManager frameBufferManager) {
        super(renderManager);

        Vector2i viewport = new Vector2i(get(CAMERA_VIEWPORT_WIDTH), get(CAMERA_VIEWPORT_HEIGHT));
        FrameBuffer frameBuffer = frameBufferManager.supply(new FrameBufferData(viewport));

        this.camera = new StaticCamera(this,
                frameBuffer,
                viewport,
                get(CAMERA_FOV),
                get(CAMERA_NEAR_PLANE),
                get(CAMERA_FAR_PLANE),
                ORTHOGRAPHIC);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void update(double delta) {
    }

    @Override
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withCamera(camera);
    }
}
