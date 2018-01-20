package piengine.visual.render.domain.fragment.domain;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.object.model.domain.Model;
import piengine.object.terrain.domain.Terrain;
import piengine.object.water.domain.Water;
import piengine.visual.camera.domain.Camera;
import piengine.visual.fog.Fog;
import piengine.visual.lighting.directional.light.domain.DirectionalLight;
import piengine.visual.lighting.point.light.domain.PointLight;
import piengine.visual.lighting.point.shadow.domain.PointShadow;
import piengine.visual.render.domain.context.RenderContext;
import piengine.visual.render.domain.plan.PlanContext;
import piengine.visual.skybox.domain.Skybox;

import java.util.ArrayList;
import java.util.List;

public class RenderWorldPlanContext implements PlanContext, RenderContext {

    public final List<Model> models;
    public final List<Terrain> terrains;
    public final List<Water> waters;
    public final List<DirectionalLight> directionalLights;
    public final List<PointLight> pointLights;
    public final Vector4f clippingPlane;
    public final Vector2i viewport;
    public final Fog fog;
    public final Skybox skybox;
    public final Camera playerCamera;

    public Camera currentCamera;
    public PointShadow currentPointShadow;

    public RenderWorldPlanContext(final Vector4f clippingPlane, final Vector2i viewport, final Camera playerCamera, final Fog fog, final Skybox skybox) {
        this.models = new ArrayList<>();
        this.terrains = new ArrayList<>();
        this.waters = new ArrayList<>();
        this.directionalLights = new ArrayList<>();
        this.pointLights = new ArrayList<>();
        this.clippingPlane = clippingPlane;
        this.viewport = viewport;
        this.playerCamera = playerCamera;
        this.fog = fog;
        this.skybox = skybox;
    }
}
