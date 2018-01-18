package piengine.object.asset.plan;

import piengine.object.asset.domain.WorldAsset;
import piengine.object.model.domain.Model;
import piengine.object.terrain.domain.Terrain;
import piengine.object.water.domain.Water;
import piengine.visual.light.domain.Light;
import piengine.visual.pointshadow.domain.PointShadow;
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

    public WorldRenderAssetContextBuilder loadPointShadows(final PointShadow... pointShadows) {
        this.assetContext.pointShadows.addAll(Arrays.asList(pointShadows));
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
            this.assetContext.lights.addAll(assetContext.lights);
            this.assetContext.shadows.addAll(assetContext.shadows);
            this.assetContext.pointShadows.addAll(assetContext.pointShadows);
        }
        return this;
    }

    public WorldRenderAssetContext build() {
        return assetContext;
    }
}
