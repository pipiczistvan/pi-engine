package piengine.object.asset.plan;

import piengine.object.model.domain.Model;
import piengine.object.terrain.domain.Terrain;
import piengine.object.water.domain.Water;
import piengine.visual.lighting.directional.light.domain.DirectionalLight;
import piengine.visual.lighting.point.light.domain.PointLight;

import java.util.ArrayList;
import java.util.List;

public class WorldRenderAssetContext implements RenderAssetContext {

    public final List<Model> models;
    public final List<Terrain> terrains;
    public final List<Water> waters;
    public final List<DirectionalLight> directionalLights;
    public final List<PointLight> pointLights;

    WorldRenderAssetContext() {
        this.models = new ArrayList<>();
        this.terrains = new ArrayList<>();
        this.waters = new ArrayList<>();
        this.directionalLights = new ArrayList<>();
        this.pointLights = new ArrayList<>();
    }
}
