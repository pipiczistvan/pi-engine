package piengine.object.asset.domain;

import piengine.object.model.domain.Model;
import piengine.object.terrain.domain.Terrain;
import piengine.object.water.domain.Water;
import piengine.visual.light.domain.Light;
import piengine.visual.render.domain.plan.WorldRenderPlanBuilder;
import piengine.visual.shadow.domain.Shadow;

public abstract class WorldAsset<T extends AssetArgument> extends Asset<T, WorldRenderPlanBuilder> {

    public Model[] getModels() {
        return new Model[0];
    }

    public Light[] getLights() {
        return new Light[0];
    }

    public Terrain[] getTerrains() {
        return new Terrain[0];
    }

    public Shadow[] getShadows() {
        return new Shadow[0];
    }

    public Water[] getWaters() {
        return new Water[0];
    }
}
