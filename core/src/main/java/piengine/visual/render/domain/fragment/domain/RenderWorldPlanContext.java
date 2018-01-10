package piengine.visual.render.domain.fragment.domain;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.object.model.domain.Model;
import piengine.object.terrain.domain.Terrain;
import piengine.object.water.domain.Water;
import piengine.visual.camera.domain.Camera;
import piengine.visual.fog.Fog;
import piengine.visual.light.Light;
import piengine.visual.render.domain.context.RenderContext;
import piengine.visual.render.domain.plan.PlanContext;
import piengine.visual.shadow.domain.Shadow;
import piengine.visual.skybox.domain.Skybox;

import java.util.List;

public class RenderWorldPlanContext implements PlanContext, RenderContext {

    public final List<Model> models;
    public final List<Terrain> terrains;
    public final List<Water> waters;
    public final List<Light> lights;
    public final List<Shadow> shadows;
    public final Vector4f clippingPlane;
    public final Vector2i viewport;
    public Camera camera;
    public final Fog fog;
    public final Skybox skybox;

    public RenderWorldPlanContext(final List<Model> models, final List<Terrain> terrains, final List<Water> waters, final List<Light> lights, final List<Shadow> shadows, final Vector4f clippingPlane, final Vector2i viewport, final Camera camera, final Fog fog, final Skybox skybox) {
        this.models = models;
        this.terrains = terrains;
        this.waters = waters;
        this.lights = lights;
        this.shadows = shadows;
        this.clippingPlane = clippingPlane;
        this.viewport = viewport;
        this.camera = camera;
        this.fog = fog;
        this.skybox = skybox;
    }
}
