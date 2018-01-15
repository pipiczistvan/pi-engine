package piengine.visual.render.domain.plan;

import org.joml.Vector2i;
import piengine.object.asset.domain.GuiAsset;
import piengine.object.asset.plan.GuiRenderAssetContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.domain.fragment.domain.RenderGuiPlanContext;

import java.util.ArrayList;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_GUI;

public class GuiRenderPlanBuilder extends RenderPlanBuilder<GuiRenderPlanBuilder, RenderGuiPlanContext> {

    GuiRenderPlanBuilder(final Vector2i viewport) {
        super(new RenderGuiPlanContext(
                viewport,
                new ArrayList<>(),
                new ArrayList<>()
        ));
    }

    public GuiRenderPlanBuilder loadAssets(final GuiAsset... assets) {
        for (GuiAsset asset : assets) {
            loadAssetContext((GuiRenderAssetContext) asset.getAssetContext());
        }
        return this;
    }

    public GuiRenderPlanBuilder loadAssetContext(final GuiRenderAssetContext... assetContexts) {
        for (GuiRenderAssetContext assetContext : assetContexts) {
            this.context.canvases.addAll(assetContext.canvases);
            this.context.texts.addAll(assetContext.texts);
        }
        return this;
    }

    @Override
    protected GuiRenderPlanBuilder thiz() {
        return this;
    }

    @Override
    protected RenderFragmentType getType() {
        return RENDER_GUI;
    }
}
