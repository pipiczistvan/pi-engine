package piengine.object.asset.plan;

import piengine.object.model.domain.Model;
import piengine.object.terrain.domain.Terrain;
import piengine.object.water.domain.Water;
import piengine.visual.light.domain.Light;
import piengine.visual.shadow.domain.Shadow;

import java.util.Arrays;

public class WorldRenderAssetContextBuilder {

    private final WorldRenderAssetContext assetContext;

    private WorldRenderAssetContextBuilder() {
        assetContext = new WorldRenderAssetContext();
    }

    public static WorldRenderAssetContextBuilder create() {
        return new WorldRenderAssetContextBuilder();
    }

    public WorldRenderAssetContextBuilder loadModels(final Model... models) {
        this.assetContext.models.addAll(Arrays.asList(models));
        return this;
    }

    public WorldRenderAssetContextBuilder loadTerrains(final Terrain... terrains) {
        this.assetContext.terrains.addAll(Arrays.asList(terrains));
        return this;
    }

    public WorldRenderAssetContextBuilder loadWaters(final Water... waters) {
        this.assetContext.waters.addAll(Arrays.asList(waters));
        return this;
    }

    public WorldRenderAssetContextBuilder loadLights(final Light... lights) {
        this.assetContext.lights.addAll(Arrays.asList(lights));
        return this;
    }

    public WorldRenderAssetContextBuilder loadShadows(final Shadow... shadows) {
        this.assetContext.shadows.addAll(Arrays.asList(shadows));
        return this;
    }

    public WorldRenderAssetContext build() {
        return assetContext;
    }
}
