package piengine.visual.render.domain.plan;

import org.joml.Vector2i;
import piengine.object.asset.domain.GuiAsset;
import piengine.object.canvas.domain.Canvas;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.domain.fragment.domain.RenderGuiPlanContext;
import piengine.visual.writing.text.domain.Text;

import java.util.ArrayList;
import java.util.Arrays;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_GUI;

public class GuiRenderPlanBuilder extends RenderPlanBuilder<GuiRenderPlanBuilder, RenderGuiPlanContext> {

    GuiRenderPlanBuilder(final Vector2i viewport) {
        super(new RenderGuiPlanContext(
                viewport,
                new ArrayList<>(),
                new ArrayList<>()
        ));
    }

    public GuiRenderPlanBuilder loadCanvases(final Canvas... canvases) {
        this.context.canvases.addAll(Arrays.asList(canvases));
        return this;
    }

    public GuiRenderPlanBuilder loadTexts(final Text... texts) {
        this.context.texts.addAll(Arrays.asList(texts));
        return this;
    }

    public GuiRenderPlanBuilder loadAssets(final GuiAsset... assets) {
        for (GuiAsset asset : assets) {
            loadCanvases(asset.getCanvases());
            loadTexts(asset.getTexts());
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
