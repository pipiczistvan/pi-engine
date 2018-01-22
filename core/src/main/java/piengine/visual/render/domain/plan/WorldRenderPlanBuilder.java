package piengine.visual.render.domain.plan;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.object.asset.domain.WorldAsset;
import piengine.object.asset.plan.WorldRenderAssetContext;
import piengine.object.camera.domain.Camera;
import piengine.object.skybox.domain.Skybox;
import piengine.visual.fog.Fog;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_WORLD;

public class WorldRenderPlanBuilder extends RenderPlanBuilder<WorldRenderPlanBuilder, RenderWorldPlanContext> {

    WorldRenderPlanBuilder(final Camera camera, final Fog fog, final Skybox skybox) {
        super(new RenderWorldPlanContext(
                new Vector4f(),
                new Vector2i(camera.viewport),
                camera,
                fog,
                skybox
        ));
    }

    public WorldRenderPlanBuilder loadAssets(final WorldAsset... assets) {
        for (WorldAsset asset : assets) {
            loadAssetContext((WorldRenderAssetContext) asset.getAssetContext());
        }
        return this;
    }

    public WorldRenderPlanBuilder loadAssetContext(final WorldRenderAssetContext... assetContexts) {
        for (WorldRenderAssetContext assetContext : assetContexts) {
            this.context.models.addAll(assetContext.models);
            this.context.terrains.addAll(assetContext.terrains);
            this.context.waters.addAll(assetContext.waters);
            this.context.directionalLights.addAll(assetContext.directionalLights);
            this.context.pointLights.addAll(assetContext.pointLights);
            this.context.animatedModels.addAll(assetContext.animatedModels);
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
