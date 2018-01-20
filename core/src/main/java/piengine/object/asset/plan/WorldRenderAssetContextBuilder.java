package piengine.object.asset.plan;

import piengine.object.asset.domain.WorldAsset;
import piengine.object.model.domain.Model;
import piengine.object.terrain.domain.Terrain;
import piengine.object.water.domain.Water;
import piengine.visual.lighting.directional.light.domain.DirectionalLight;
import piengine.visual.lighting.point.light.domain.PointLight;

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

    public WorldRenderAssetContextBuilder loadDirectionalLights(final DirectionalLight... directionalLights) {
        this.assetContext.directionalLights.addAll(Arrays.asList(directionalLights));
        return this;
    }

    public WorldRenderAssetContextBuilder loadPointLights(final PointLight... pointLights) {
        this.assetContext.pointLights.addAll(Arrays.asList(pointLights));
        return this;
    }

    public WorldRenderAssetContextBuilder loadAssets(final WorldAsset... assets) {
        for (WorldAsset asset : assets) {
            loadAssetContext((WorldRenderAssetContext) asset.getAssetContext());
        }
        return this;
    }

    public WorldRenderAssetContextBuilder loadAssetContext(final WorldRenderAssetContext... assetContexts) {
        for (WorldRenderAssetContext assetContext : assetContexts) {
            this.assetContext.models.addAll(assetContext.models);
            this.assetContext.terrains.addAll(assetContext.terrains);
            this.assetContext.waters.addAll(assetContext.waters);
            this.assetContext.directionalLights.addAll(assetContext.directionalLights);
            this.assetContext.pointLights.addAll(assetContext.pointLights);
        }
        return this;
    }

    public WorldRenderAssetContext build() {
        return assetContext;
    }
}
