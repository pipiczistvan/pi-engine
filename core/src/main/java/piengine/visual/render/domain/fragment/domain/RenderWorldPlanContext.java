package piengine.visual.render.domain.fragment.domain;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.object.asset.plan.WorldRenderAssetContext;
import piengine.object.camera.domain.Camera;
import piengine.object.skybox.domain.Skybox;
import piengine.visual.fog.Fog;
import piengine.visual.lighting.point.shadow.domain.PointShadow;
import piengine.visual.render.domain.context.RenderContext;
import piengine.visual.render.domain.plan.PlanContext;

public class RenderWorldPlanContext extends WorldRenderAssetContext implements PlanContext, RenderContext {

    public final Vector4f clippingPlane;
    public final Vector2i viewport;
    public final Fog fog;
    public final Skybox skybox;
    public final Camera playerCamera;

    public Camera currentCamera;
    public PointShadow currentPointShadow;

    public RenderWorldPlanContext(final Vector4f clippingPlane, final Vector2i viewport, final Camera playerCamera, final Fog fog, final Skybox skybox) {
        this.clippingPlane = clippingPlane;
        this.viewport = viewport;
        this.playerCamera = playerCamera;
        this.fog = fog;
        this.skybox = skybox;
    }
}
