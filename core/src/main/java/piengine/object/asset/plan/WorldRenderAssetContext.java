package piengine.object.asset.plan;

import piengine.object.animatedmodel.domain.AnimatedModel;
import piengine.object.model.domain.Model;
import piengine.object.particlesystem.domain.ParticleSystem;
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
    public final List<AnimatedModel> animatedModels;
    public final List<ParticleSystem> particleSystems;

    public WorldRenderAssetContext() {
        this.models = new ArrayList<>();
        this.terrains = new ArrayList<>();
        this.waters = new ArrayList<>();
        this.directionalLights = new ArrayList<>();
        this.pointLights = new ArrayList<>();
        this.animatedModels = new ArrayList<>();
        this.particleSystems = new ArrayList<>();
    }
}
