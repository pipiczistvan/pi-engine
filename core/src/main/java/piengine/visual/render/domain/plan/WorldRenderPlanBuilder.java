package piengine.visual.render.domain.plan;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.object.asset.domain.WorldAsset;
import piengine.object.model.domain.Model;
import piengine.object.terrain.domain.Terrain;
import piengine.object.water.domain.Water;
import piengine.visual.camera.domain.Camera;
import piengine.visual.fog.Fog;
import piengine.visual.light.domain.Light;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.shadow.domain.Shadow;
import piengine.visual.skybox.domain.Skybox;

import java.util.ArrayList;
import java.util.Arrays;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_WORLD;

public class WorldRenderPlanBuilder extends RenderPlanBuilder<WorldRenderPlanBuilder, RenderWorldPlanContext> {

    WorldRenderPlanBuilder(final Camera camera, final Fog fog, final Skybox skybox) {
        super(new RenderWorldPlanContext(
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new Vector4f(),
                new Vector2i(camera.viewport),
                camera,
                fog,
                skybox
        ));
    }

    public WorldRenderPlanBuilder loadModels(final Model... models) {
        this.context.models.addAll(Arrays.asList(models));
        return this;
    }

    public WorldRenderPlanBuilder loadTerrains(final Terrain... terrains) {
        this.context.terrains.addAll(Arrays.asList(terrains));
        return this;
    }

    public WorldRenderPlanBuilder loadWaters(final Water... waters) {
        this.context.waters.addAll(Arrays.asList(waters));
        return this;
    }

    public WorldRenderPlanBuilder loadLights(final Light... lights) {
        this.context.lights.addAll(Arrays.asList(lights));
        return this;
    }

    public WorldRenderPlanBuilder loadShadows(final Shadow... shadows) {
        this.context.shadows.addAll(Arrays.asList(shadows));
        return this;
    }

    public WorldRenderPlanBuilder loadAssets(final WorldAsset... assets) {
        for (WorldAsset asset : assets) {
            loadModels(asset.getModels());
            loadLights(asset.getLights());
            loadTerrains(asset.getTerrains());
            loadShadows(asset.getShadows());
            loadWaters(asset.getWaters());
        }
        return this;
    }

    @Override
    protected WorldRenderPlanBuilder thiz() {
        return this;
    }

    @Override
    protected RenderFragmentType getType() {
        return RENDER_WORLD;
    }
}
