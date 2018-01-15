package piengine.object.asset.plan;

import piengine.object.model.domain.Model;
import piengine.object.terrain.domain.Terrain;
import piengine.object.water.domain.Water;
import piengine.visual.light.domain.Light;
import piengine.visual.shadow.domain.Shadow;

import java.util.ArrayList;
import java.util.List;

public class WorldRenderAssetContext implements RenderAssetContext {

    public final List<Model> models;
    public final List<Terrain> terrains;
    public final List<Water> waters;
    public final List<Light> lights;
    public final List<Shadow> shadows;

    WorldRenderAssetContext() {
        this.models = new ArrayList<>();
        this.terrains = new ArrayList<>();
        this.waters = new ArrayList<>();
        this.lights = new ArrayList<>();
        this.shadows = new ArrayList<>();
    }
}
