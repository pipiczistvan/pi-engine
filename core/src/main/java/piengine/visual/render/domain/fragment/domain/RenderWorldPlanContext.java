package piengine.visual.render.domain.fragment.domain;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.object.model.domain.Model;
import piengine.object.terrain.domain.Terrain;
import piengine.object.water.domain.Water;
import piengine.visual.camera.domain.Camera;
import piengine.visual.fog.Fog;
import piengine.visual.light.domain.Light;
import piengine.visual.pointshadow.domain.PointShadow;
import piengine.visual.render.domain.context.RenderContext;
import piengine.visual.render.domain.plan.PlanContext;
import piengine.visual.shadow.domain.Shadow;
import piengine.visual.skybox.domain.Skybox;

import java.util.ArrayList;
import java.util.List;

public class RenderWorldPlanContext implements PlanContext, RenderContext {

    public PointShadow currentPointShadow;

    public final List<Model> models;
    public final List<Terrain> terrains;
    public final List<Water> waters;
    public final List<Light> lights;
    public final List<Shadow> shadows;
    public final List<PointShadow> pointShadows;
    public final Vector4f clippingPlane;
    public final Vector2i viewport;
    public Camera camera;
    public final Fog fog;
    public final Skybox skybox;

    public RenderWorldPlanContext(final Vector4f clippingPlane, final Vector2i viewport, final Camera camera, final Fog fog, final Skybox skybox) {
        this.models = new ArrayList<>();
        this.terrains = new ArrayList<>();
        this.waters = new ArrayList<>();
        this.lights = new ArrayList<>();
        this.shadows = new ArrayList<>();
        this.pointShadows = new ArrayList<>();
        this.clippingPlane = clippingPlane;
        this.viewport = viewport;
        this.camera = camera;
        this.fog = fog;
        this.skybox = skybox;
    }
}
